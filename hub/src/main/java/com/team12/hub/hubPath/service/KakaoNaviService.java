package com.team12.hub.hubPath.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class KakaoNaviService {
    @Value("${kakao.api.key}")
    private String kakaoApiKey;

    // 허브 간 이동 거리과 이동 거리 가져오기
    // priority: distance 최단 경로 기준
    // alternatives 대안 경로 미제공
    // summary: true 요약 정보 제공(요약 정보에 이동 시간 & 이동 거리가 있음)
    private static final String NAVI_API_URL = "https://apis-navi.kakaomobility.com/v1/directions?origin=%s&destination=%s&summary=true";

    public List<Integer> getDistanceAndDuration(String from_hub_latitude, String from_hub_longitude, String to_hub_latitude, String to_hub_longitude){
        String origin = from_hub_longitude + "," + from_hub_latitude;
        String destination = to_hub_longitude + "," + to_hub_latitude;
        String url = String.format(NAVI_API_URL, origin, destination);

        // URL 로그 출력
        log.info("API 호출 URL: {}", url);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + kakaoApiKey);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // API 호출
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        // 응답 결과 로그 출력
        log.info("API 응답: {}", response.getBody());

        // JSON 응답 파싱
        JSONObject jsonResponse = new JSONObject(response.getBody());
        JSONArray routes = jsonResponse.getJSONArray("routes");

        if (routes.length() > 0) {
            JSONObject summary = routes.getJSONObject(0).getJSONObject("summary");
            int distance = summary.getInt("distance"); // 거리 (단위: 미터)
            int duration = summary.getInt("duration"); // 시간 (단위: 초)
            log.info("Kakao Navi 응답 distance: {}", distance);
            log.info("Kakao Navi 응답 duration: {}", duration);
            return List.of(distance, duration);
        } else{
          throw new RuntimeException("경로를 찾지 못하여 이동 거리와 시간을 구하지 못했습니다. ");
        }
    }
}
