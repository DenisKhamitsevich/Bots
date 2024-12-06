package org.task.robots.logger.implementation;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.task.robots.dto.BotDto;
import org.task.robots.dto.GeneralizedDto;
import org.task.robots.logger.EventLogger;

import java.util.List;

import static org.task.robots.enums.MessageType.BOTS;
import static org.task.robots.enums.MessageType.LOG;

@Service
public class WebsocketEventLogger implements EventLogger {
    private final SimpMessagingTemplate simpMessagingTemplate;

    public WebsocketEventLogger(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    private final String WEBSOCKET_LOG_TOPIC_URL = "/topic/logs";

    @Override
    public void log(String message) {
        GeneralizedDto dto = new GeneralizedDto(LOG, message);
        simpMessagingTemplate.convertAndSend(WEBSOCKET_LOG_TOPIC_URL, dto);
    }

    @Override
    public void logListOfBots(List<BotDto> bots) {
        GeneralizedDto dto = new GeneralizedDto(BOTS, bots);
        simpMessagingTemplate.convertAndSend(WEBSOCKET_LOG_TOPIC_URL, dto);
    }
}
