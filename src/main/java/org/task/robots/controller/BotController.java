package org.task.robots.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.task.robots.dto.BotDto;
import org.task.robots.enums.BotType;
import org.task.robots.service.BotService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bot")
@AllArgsConstructor
public class BotController {

    private final BotService botService;

    @GetMapping()
    public List<BotDto> getBots() {
        return botService.getBots();
    }

    @PostMapping("{id}")
    public void writeEventByBotId(@PathVariable int id) {
        botService.writeEventByBotId(id);
    }

    @PostMapping()
    public void writeEventByBotType(@RequestParam BotType type) {
        botService.writeEventByBotType(type);
    }

    @DeleteMapping("{id}")
    public void destroyEventByBotId(@PathVariable int id) {
        botService.destroyEventByBotId(id);
    }
}
