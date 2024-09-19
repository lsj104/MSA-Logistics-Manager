package com.team12.hub.client;


import com.team12.common.exception.response.SuccessResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name = "ai")
public interface AIClient {

    // 단건 허브에 대한 간선 추천
    @GetMapping("/api/gemini")
    String askQuestion(@RequestParam String requestContents);

}
