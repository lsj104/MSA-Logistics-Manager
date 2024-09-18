package com.team12.ai.gemini.dto;

import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
public class AiRequest {
  private List<Content> contents; // 수정: "contents"라는 필드가 있을 가능성이 있음

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Content {
    private List<Part> parts; // 수정: "parts" 대신 "text"나 다른 필드를 사용할 수도 있음
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Part {
    private String text;
  }

  // 요청 본문 생성 메서드
  public static AiRequest generateBody(String text) {
    Part part = new Part(text);  // 텍스트를 포함하는 객체 생성
    Content content = new Content(List.of(part));  // 내용 생성
    return new AiRequest(List.of(content));  // 최종 요청 객체 생성
  }
}
