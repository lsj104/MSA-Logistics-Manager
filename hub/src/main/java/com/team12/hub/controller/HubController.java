package com.team12.hub.controller;

import com.team12.hub.dto.HubRequestDto;
import com.team12.hub.dto.HubResponseDto;
import com.team12.hub.service.HubService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/api/hubs")
@Controller
public class HubController {
    private final HubService hubService;
    @PostMapping
    public void createHub(@RequestBody HubRequestDto hubRequestDto){
        hubService.createHub(hubRequestDto);
    }
    @PutMapping
    public void updateHub(@PathVariable UUID hubId, @RequestBody HubRequestDto hubRequestDto){
        hubService.updateHub(hubId, hubRequestDto);
    }
    @DeleteMapping
    public void deleteHub(@PathVariable UUID hubId){
        hubService.deleteHub(hubId);
    }
    @GetMapping
    public HubResponseDto getHub(@PathVariable UUID hubId){
        return hubService.getHub(hubId);
    }
}
