package com.team12.slack.dto;

import lombok.Data;

@Data
public class SlackRequestDto {
    private String email;
    private String content;
}
