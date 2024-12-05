package org.task.robots.task.implementation;

import org.task.robots.enums.BotType;
import org.task.robots.task.AbstractTask;

import static org.task.robots.enums.TaskType.WRITE;

public class WriteTask extends AbstractTask {
    public WriteTask(BotType botType) {
        this.taskType = WRITE;
        this.botType = botType;
    }

    public WriteTask(Integer threadId) {
        this.taskType = WRITE;
        this.threadId = threadId;
    }

    @Override
    public void run() {
        String info = this.threadId != null ? "task with ID: " + threadId : "task for " + botType.name() + " bot type";
        System.out.println("Executing  " + info + " on thread " + Thread.currentThread().getName());
    }
}
