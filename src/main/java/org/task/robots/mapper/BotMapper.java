package org.task.robots.mapper;

import org.task.robots.bot.AbstractBot;
import org.task.robots.dto.BotDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class BotMapper {
    public static List<BotDto> botMapToBotDto(Map<Integer, AbstractBot> values) {
        List<BotDto> botDtos = new ArrayList<>();
        for (Map.Entry<Integer, AbstractBot> entry : values.entrySet()) {
            botDtos.add(new BotDto(entry.getKey(), entry.getValue().getName()));
        }
        return botDtos;
    }
}
