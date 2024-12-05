package org.task.robots.task;

import lombok.Getter;
import lombok.ToString;
import org.task.robots.enums.BotType;
import org.task.robots.enums.TaskType;


@Getter
@ToString
public abstract class AbstractTask implements Runnable {
    protected Integer threadId = null;
    protected BotType botType = null;
    protected TaskType taskType = null;
}
