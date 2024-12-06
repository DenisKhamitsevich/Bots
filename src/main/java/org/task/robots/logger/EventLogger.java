package org.task.robots.logger;

import org.task.robots.dto.BotDto;

import java.util.List;

public interface EventLogger {
    void log(String message);

    void logListOfBots(List<BotDto> bots);
}
