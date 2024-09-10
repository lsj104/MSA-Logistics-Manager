package com.team12.slack.controller;

import com.team12.slack.dto.SlackRequestDto;
import com.team12.slack.service.SlackService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/slack")
@Slf4j
public class SlackController {
    private final SlackService slackService;

    // todo : response entity 형식 변경
    @PostMapping("")
    public String sendMessage(@RequestBody SlackRequestDto request) {
        return slackService.sendMessageToUser(request);
    }


}
