package com.team12.hub.hubPath.controller;

import com.team12.hub.hubPath.domain.HubPath;
import com.team12.hub.hubPath.dto.HubPathRequestDto;
import com.team12.hub.hubPath.dto.HubPathResponseDto;
import com.team12.hub.hubPath.service.HubPathService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/api/hub-paths")
@RestController
public class HubPathController {
    private final HubPathService hubPathService;

    @PostMapping
    public HubPathResponseDto createHubPath(@RequestBody HubPathRequestDto hubPathRequestDto) {
        return hubPathService.createHubPath(hubPathRequestDto);
    }

    @PutMapping("/{hubPathId}")
    public HubPath updateHubPath(@PathVariable UUID hubPathId, @RequestBody HubPathRequestDto hubPathRequestDto) {
        return hubPathService.updateHubPath(hubPathId, hubPathRequestDto);
    }

    @DeleteMapping("/{hubPathId}")
    public UUID deleteHubPath(@PathVariable UUID hubPathId) {
        return hubPathService.deleteHubPath(hubPathId);
    }

    @GetMapping("/{hubPathId}")
    public HubPath getHubPath(@PathVariable UUID hubPathId) {
        return hubPathService.getHubPath(hubPathId);
    }
}
