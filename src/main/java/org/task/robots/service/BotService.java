package org.task.robots.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.task.robots.dto.BotDto;
import org.task.robots.enums.BotType;
import org.task.robots.executor.CustomTaskExecutor;
import org.task.robots.task.AbstractTask;
import org.task.robots.task.TaskFactory;

import java.util.List;

import static org.task.robots.enums.TaskType.DESTROY;
import static org.task.robots.enums.TaskType.WRITE;

@Service
@AllArgsConstructor
public class BotService {
    private final CustomTaskExecutor taskExecutor;
    private final CustomTaskExecutor customTaskExecutor;

    public void writeEventByBotId(Integer id) {
        AbstractTask task = TaskFactory.createTask(WRITE, null, id);
        taskExecutor.submit(task);
    }

    public void writeEventByBotType(BotType type) {
        AbstractTask task = TaskFactory.createTask(WRITE, type, null);
        taskExecutor.submit(task);
    }

    public void destroyEventByBotId(Integer id) {
        AbstractTask task = TaskFactory.createTask(DESTROY, null, id);
        taskExecutor.submit(task);
    }

    public List<BotDto> getBots() {
        return customTaskExecutor.getBots();
    }
}
