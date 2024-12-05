package org.task.robots.task.implementation;

import org.task.robots.executor.CustomTaskExecutor;
import org.task.robots.task.AbstractTask;

import static org.task.robots.enums.TaskType.DESTROY;


public class DestroyTask extends AbstractTask {

    public DestroyTask(Integer threadId) {
        this.taskType = DESTROY;
        this.threadId = threadId;
    }

    @Override
    public void run() {
        System.out.println("Destroying current thread " + Thread.currentThread().getName());
        CustomTaskExecutor.removeThread(Thread.currentThread(), this.threadId);
    }
}
