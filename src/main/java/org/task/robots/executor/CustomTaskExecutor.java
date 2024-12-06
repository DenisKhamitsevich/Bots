package org.task.robots.executor;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.task.robots.bot.AbstractBot;
import org.task.robots.bot.BotFactory;
import org.task.robots.dto.BotDto;
import org.task.robots.enums.BotType;
import org.task.robots.logger.EventLogger;
import org.task.robots.mapper.BotMapper;
import org.task.robots.task.AbstractTask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class CustomTaskExecutor {
    /**
     * Map of individual task queues (queues for tasks with specified bot id), where key - bot id, value - queue of tasks
     */
    private final static Map<Integer, BlockingQueue<Runnable>> individualTaskQueues = new HashMap<>();
    /**
     * Map of task queues (queues for tasks with specified bot type), where key - bot type, value - queue of tasks
     */
    private final static Map<BotType, BlockingQueue<Runnable>> taskQueues = new HashMap<>();
    /**
     * Map of active bots, where key - bot id, value - bot variable
     */
    private final static Map<Integer, AbstractBot> bots = new HashMap<>();

    private final EventLogger eventLogger;

    @Value("${executor.thread-pool-default-size}")
    int threadCount;
    @Value("${executor.scheduler-task-queue-size}")
    int schedulerTaskQueueSize;
    /**
     * counter for thread id
     */
    private int threadIdCounter = 1;

    public CustomTaskExecutor(EventLogger eventLogger) {
        this.eventLogger = eventLogger;
    }


    @PostConstruct
    public void init() {
        for (BotType botType : BotType.values()) {
            BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();
            taskQueues.put(botType, queue);
            for (int i = 1; i <= threadCount; i++) {
                createBot(botType);
            }
        }
    }

    /**
     * Submits task into specific queue
     */
    public void submit(AbstractTask task) {
        BlockingQueue<Runnable> requiredQueue;
        eventLogger.log("Creating task ...");
        Integer threadId = task.getThreadId();
        requiredQueue = threadId != null ? individualTaskQueues.get(threadId) :
                taskQueues.get(task.getBotType());

        if (requiredQueue != null) {
            try {
                requiredQueue.put(task);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Failed to submit task: " + e.getMessage());
            }
        } else {
            System.err.println("No thread available for Task ID: " + threadId);
        }
    }

    /**
     * Creates bot of specified type
     */
    private AbstractBot createBot(BotType botType) {
        eventLogger.log("Creating bot of type " + botType);
        BlockingQueue<Runnable> taskQueue = taskQueues.get(botType);
        AbstractBot thread = BotFactory.createBot(botType, taskQueue, botType.name() + "-" + threadIdCounter);
        individualTaskQueues.put(threadIdCounter, thread.getIndividualTaskQueue());
        bots.put(threadIdCounter, thread);
        thread.start();
        threadIdCounter++;
        eventLogger.logListOfBots(getBots());
        return thread;
    }

    /**
     * Removes (interrupts) given thread adn removes it from task executor
     */
    public void removeThread(Thread thread, Integer threadId) {
        thread.interrupt();
        bots.remove(threadId);
        individualTaskQueues.remove(threadId);
        eventLogger.logListOfBots(getBots());
    }

    public List<BotDto> getBots() {
        return BotMapper.botMapToBotDto(bots);
    }

    /**
     * Scans for overflowed(amount of tasks in a queue bigger than schedulerTaskQueueSize) queues and creates new threads if needed
     */
    @Scheduled(fixedDelay = 5000)
    private void checkQueues() {
        for (BotType botType : BotType.values()) {
            BlockingQueue<Runnable> queue = taskQueues.get(botType);
            if (queue.size() >= schedulerTaskQueueSize)
                createBot(botType);
        }
    }

    @PreDestroy
    public void shutdown() {
        for (AbstractBot thread : bots.values()) {
            System.out.println("Shutting down thread: " + thread.getName());
            thread.interrupt();
        }
    }
}
