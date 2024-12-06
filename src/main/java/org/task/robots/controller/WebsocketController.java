package org.task.robots.controller;

import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.task.robots.enums.BotType;
import org.task.robots.service.BotService;

@RestController
@AllArgsConstructor
public class WebsocketController {
    private final BotService botService;

    @MessageMapping("/bots")
    @SendTo("/topic/logs")
    public void logListOfBots() {
        botService.logListOfBots();
    }

    @MessageMapping("/task/create/by-id")
    public void writeEventByBotId(@RequestBody Integer id) {
        botService.writeEventByBotId(id);
    }

    @MessageMapping("/task/create/by-type")
    public void writeEventByBotType(@RequestBody String type) {
        botService.writeEventByBotType(BotType.valueOf(type));
    }

    @MessageMapping("/delete/bot")
    public void destroyEventByBotId(@RequestBody Integer id) {
        botService.destroyEventByBotId(id);
    }
}
