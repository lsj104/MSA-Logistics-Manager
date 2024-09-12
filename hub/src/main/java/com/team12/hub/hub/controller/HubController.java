package com.team12.hub.hub.controller;

import com.team12.hub.hub.dto.HubRequestDto;
import com.team12.hub.hub.dto.HubResponseDto;
import com.team12.hub.hub.service.HubService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/api/hubs")
@RestController
public class HubController {
    private final HubService hubService;
    @PostMapping
    public void createHub(@RequestBody HubRequestDto hubRequestDto){
        hubService.createHub(hubRequestDto);
    }
    @PutMapping("/{hubId}")
    public void updateHub(@PathVariable UUID hubId, @RequestBody HubRequestDto hubRequestDto){
        hubService.updateHub(hubId, hubRequestDto);
    }
    @DeleteMapping("/{hubId}")
    public void deleteHub(@PathVariable UUID hubId){
        hubService.deleteHub(hubId);
    }
    @GetMapping("/{hubId}")
    public HubResponseDto getHub(@PathVariable UUID hubId){
        return hubService.getHub(hubId);
    }
}
