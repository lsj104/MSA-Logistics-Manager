package com.team12.ai.gemini.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AiResponse{
  private List<Candidate> candidates;

  @ToString
  @Getter
  @Setter
  @NoArgsConstructor
  // response에서 role, usagemetirc (?) 등 정의 되지 않은 필드들이 있어 넣었습니당
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Candidate {
    private Content content;
    private String finishReason;
    private int index;
    private List<SafetyRating> safetyRatings;

    @ToString
    @Getter
    @Setter
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Content {
      private List<Part> parts;

      @ToString
      @Getter
      @Setter
      @NoArgsConstructor
      public static class Part {
        private String text;
      }
    }
  }

  @Getter
  @Setter
  @NoArgsConstructor
  public static class SafetyRating {
    private String category;
    private String probability;
  }
}


