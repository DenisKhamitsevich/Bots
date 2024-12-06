package org.task.robots.task.implementation;

import org.task.robots.enums.BotType;
import org.task.robots.logger.EventLogger;
import org.task.robots.task.AbstractTask;

import static org.task.robots.enums.TaskType.WRITE;

public class WriteTask extends AbstractTask {
    public WriteTask(BotType botType, EventLogger eventLogger) {
        super(eventLogger);
        this.taskType = WRITE;
        this.botType = botType;
    }

    public WriteTask(Integer threadId, EventLogger eventLogger) {
        super(eventLogger);
        this.taskType = WRITE;
        this.threadId = threadId;
    }


    @Override
    public void run() {
        String info = this.threadId != null ? "task with ID: " + threadId : "task for " + botType.name() + " bot type";
        eventLogger.log("Executing  write " + info + " on thread " + Thread.currentThread().getName());
        //imitation of work
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        eventLogger.log("Finished executing write " + info + " on thread " + Thread.currentThread().getName());
    }
}
