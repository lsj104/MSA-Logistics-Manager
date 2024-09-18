package com.team12.slack.dto;

import com.team12.slack.domain.Message;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class SlackResDto {
    private UUID id;
    private String content;
    private LocalDateTime sendAt;

    // Message 객체를 매핑하는 생성자 추가
    public SlackResDto(Message message) {
        this.id = message.getId();
        this.content = message.getContent();
        this.sendAt = message.getSend_at();
    }
}
