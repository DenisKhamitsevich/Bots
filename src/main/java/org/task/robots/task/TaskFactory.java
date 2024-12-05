package org.task.robots.task;

import org.task.robots.enums.BotType;
import org.task.robots.enums.TaskType;
import org.task.robots.task.implementation.DestroyTask;
import org.task.robots.task.implementation.WriteTask;

public class TaskFactory {
    public static AbstractTask createTask(TaskType taskType, BotType botType, Integer threadId) {
        return switch (taskType) {
            case WRITE -> botType != null ? new WriteTask(botType) : new WriteTask(threadId);
            case DESTROY -> new DestroyTask(threadId);
        };
    }
}
