package org.task.robots.task;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.task.robots.enums.BotType;
import org.task.robots.enums.TaskType;
import org.task.robots.executor.CustomTaskExecutor;
import org.task.robots.logger.EventLogger;
import org.task.robots.task.implementation.DestroyTask;
import org.task.robots.task.implementation.WriteTask;

@Component
@AllArgsConstructor
public class TaskFactory {
    private final EventLogger eventLogger;
    private final CustomTaskExecutor customTaskExecutor;

    public AbstractTask createTask(TaskType taskType, BotType botType, Integer threadId) {
        return switch (taskType) {
            case WRITE -> botType != null ? new WriteTask(botType, eventLogger) : new WriteTask(threadId, eventLogger);
            case DESTROY -> new DestroyTask(threadId, eventLogger, customTaskExecutor);
        };
    }
}
