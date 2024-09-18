package com.team12.common.dto.slack;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SlackRequestDto {
    private String email;
//    private String userId;
    private String content;

    public SlackRequestDto(String email, String content) {
        this.email = email;
        this.content = content;
    }

}
