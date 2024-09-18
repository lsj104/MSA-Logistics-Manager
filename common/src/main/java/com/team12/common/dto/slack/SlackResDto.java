package com.team12.common.dto.slack;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class SlackResDto {
    private UUID id;
    private String content;
    private LocalDateTime sendAt;

}
