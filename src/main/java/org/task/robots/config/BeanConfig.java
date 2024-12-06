package org.task.robots.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.task.robots.logger.EventLogger;
import org.task.robots.logger.implementation.WebsocketEventLogger;

@Configuration
@AllArgsConstructor
public class BeanConfig {
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Bean
    public EventLogger eventLogger() {
        return new WebsocketEventLogger(simpMessagingTemplate);
    }
}
