package com.team12.slack.controller;

import com.team12.slack.dto.SlackRequestDto;
import com.team12.slack.service.SlackService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


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

    @GetMapping("/all")
    public ResponseEntity<?> getSlackAll(@RequestHeader(value = "User-Id", required = true) String userId,
                                         @RequestHeader(value = "Role", required = true) String role,
                                         @RequestParam(value = "email", required = true) String email,
                                         Pageable pageable) {
        // if(!userId.equals(targetUserId) && !role.equals("MANAGER")) {
        //     return ResponseEntity.status(403).body("권한이 없습니다.");
        // }
        // return ResponseEntity.status(200).body(slackService.getSlackAll(targetUserId, pageable));
        return ResponseEntity.status(200).body(slackService.getSlackAll(email, pageable));
    }

    @GetMapping("")
    public ResponseEntity<?> getSlack(@RequestHeader(value = "User-Id", required = true) String userId,
                                     @RequestHeader(value = "Role", required = true) String role,
                                     @RequestParam(value= "messageId", required = true) UUID messageId,
                                     Pageable pageable) {

        // if(!userId.equals(targetUserId) && !role.equals("MANAGER")) {
        //     return ResponseEntity.status(403).body("권한이 없습니다.");
        // }
        // return ResponseEntity.status(200).body(slackService.getSlack(targetUserId, pageable));

        return ResponseEntity.status(200).body(slackService.getSlack(messageId, pageable));

    }


}
