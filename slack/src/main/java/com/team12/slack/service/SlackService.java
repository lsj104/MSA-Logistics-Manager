package com.team12.slack.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team12.slack.domain.Message;
import com.team12.slack.dto.SlackRequestDto;
import com.team12.slack.dto.SlackResDto;
import com.team12.slack.repository.SlackRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class SlackService {

    private final SlackRepository slackRepository;
    private final RestTemplate restTemplate = new RestTemplate();
    @Value("${SLACK_TOKEN}")
    private String slackToken;

    private static final String SLACK_API_URL = "https://slack.com/api/chat.postMessage";

    public String sendMessageToUser(SlackRequestDto request) {
        try {
            // todo : Feign Client로 user 정보 가져오기
//            User user = userRepository.findById(Long.parseLong(email)).orElseThrow(() -> new BaseException(ErrorCode.USER_NOT_FOUND));
//            String slackUserId = getSlackUserId(user.getSlackEmail());


            String slackUserId = getSlackUserId(request.getEmail());
            if (slackUserId.isEmpty()) {
                return "이메일 주소에 해당하는 사용자를 찾을 수 없습니다.";
            }
//            Message message = new Message(user, request.getContent());
            Message message = new Message(request.getEmail(), request.getContent());
            slackRepository.save(message);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(slackToken);

            String requestBody = String.format("{\"channel\":\"%s\", \"text\":\"%s\"}", slackUserId, request.getContent());
            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(SLACK_API_URL, entity, String.class);
            return "메시지를 성공적으로 전송했습니다.";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String getSlackUserId(String email) {
        String url = "https://slack.com/api/users.lookupByEmail";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(slackToken);

        HttpEntity<String> request = new HttpEntity<>(headers);

        try {
            String apiUrlWithParams = url + "?email=" + email;
            ResponseEntity<String> response = restTemplate.exchange(apiUrlWithParams, HttpMethod.GET, request, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode root = objectMapper.readTree(response.getBody());
                if (root.path("ok").asBoolean()) {
                    return root.path("user").path("id").asText();
                }
            }
            return "이메일 주소에 해당하는 사용자를 찾을 수 없습니다.";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public Page<SlackResDto> getSlackAll(String email, Pageable pageable) {
        // return slackRepository.findById(targetUserId, pageable).map(SlackDto::new);
        return slackRepository.findByEmail(email, pageable)
                .map(SlackResDto::new); // Message를 SlackResDto로 변환
    }

    public Object getSlack(UUID messageId, Pageable pageable) {
        // return slackRepository.findById(targetUserId, pageable).map(SlackDto::new);
        return slackRepository.findById(messageId)
                .map(SlackResDto::new); // Message를 SlackResDto로 변환
    }
}
