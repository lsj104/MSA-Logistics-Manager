package com.team12.ai.gemini.controller;

import com.team12.ai.gemini.service.GeminiApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/gemini")
@RestController
public class GeminiController {

    private final GeminiApiService geminiApiService;

    @GetMapping
    public String askQuestion(@RequestParam String requestContents){
        String responseContents = geminiApiService.question(requestContents);
        return responseContents;
    }
}
