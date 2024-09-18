package com.team12.ai.gemini.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team12.ai.gemini.dto.AiRequest;
import com.team12.ai.gemini.dto.AiResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class GeminiApiService {

  private final RestTemplate restTemplate;

  @Value("${API_KEY}")
  private String apiKey;
  private static final String baseURL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent?key=";

  public String generateContent(String question)  {
    String url = baseURL + apiKey;

    AiRequest request = AiRequest.generateBody(question);
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<AiRequest> requestEntity = new HttpEntity<>(request, headers);

    ResponseEntity<String> response = restTemplate.exchange(
        url,
        HttpMethod.POST,
        requestEntity,
        String.class
    );

    try {
      ObjectMapper mapper = new ObjectMapper();
      AiResponse aiResponse = mapper.readValue(response.getBody(), AiResponse.class);
      if (response.getStatusCode().is2xxSuccessful()) {
        return extractTextFromAiResponse(aiResponse);
      }else{
        throw new RuntimeException("Failed to generate content");
      }
    } catch (JsonProcessingException e) {
      log.error(e.getMessage());
      return null;
    }

  }

  public String extractTextFromAiResponse(AiResponse aiResponse) {
    // candidates 리스트가 비어 있지 않은지 확인
    List<AiResponse.Candidate> candidates = aiResponse.getCandidates();
    if (candidates != null && !candidates.isEmpty()) {
      AiResponse.Candidate firstCandidate = candidates.get(0);

      // content의 parts 리스트가 비어 있지 않은지 확인
      AiResponse.Candidate.Content content = firstCandidate.getContent();
      if (content != null) {
        List<AiResponse.Candidate.Content.Part> parts = content.getParts();
        if (parts != null && !parts.isEmpty()) {
          // 첫 번째 파트의 텍스트 값 추출
          return parts.get(0).getText();
        }
      }
    }
    // 만약 candidates 리스트가 비어 있거나, content 또는 parts가 비어 있으면 null 반환
    return null;
  }

}
