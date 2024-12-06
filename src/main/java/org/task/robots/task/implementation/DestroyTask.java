package org.task.robots.task.implementation;

import org.task.robots.executor.CustomTaskExecutor;
import org.task.robots.logger.EventLogger;
import org.task.robots.task.AbstractTask;

import static org.task.robots.enums.TaskType.DESTROY;


public class DestroyTask extends AbstractTask {

    private final CustomTaskExecutor customTaskExecutor;

    public DestroyTask(Integer threadId, EventLogger eventLogger, CustomTaskExecutor customTaskExecutor) {
        super(eventLogger);
        this.customTaskExecutor = customTaskExecutor;
        this.taskType = DESTROY;
        this.threadId = threadId;
    }

    @Override
    public void run() {
        eventLogger.log("Destroying current thread " + Thread.currentThread().getName());
        customTaskExecutor.removeThread(Thread.currentThread(), this.threadId);
    }
}
