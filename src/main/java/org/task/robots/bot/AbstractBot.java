package org.task.robots.bot;

import lombok.Getter;
import org.task.robots.enums.BotType;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public abstract class AbstractBot extends Thread {
    /**
     * Queue of individual tasks
     */
    @Getter
    private final BlockingQueue<Runnable> individualTaskQueue = new LinkedBlockingQueue<>();
    /**
     * Queue of general tasks (tasks by bot type)
     */
    private final BlockingQueue<Runnable> taskQueue;
    protected BotType type;

    protected AbstractBot(BlockingQueue<Runnable> taskQueue, String name) {
        super(name);
        this.taskQueue = taskQueue;
    }

    @Override
    public void run() {
        try {
            while (!isInterrupted()) {
                Runnable task = null;
                // Attempting to take a task from an individual queue
                if (!individualTaskQueue.isEmpty()) {
                    task = individualTaskQueue.take();
                } else if (!taskQueue.isEmpty()) {
                    // If the individual queue is empty, take from the general queue
                    task = taskQueue.take();
                }
                if (task != null) {
                    task.run();
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }


}
