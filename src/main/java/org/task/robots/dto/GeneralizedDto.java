package org.task.robots.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.task.robots.enums.MessageType;

@AllArgsConstructor
@JsonSerialize
@Getter
public class GeneralizedDto {
    private MessageType type;
    private Object data;
}
