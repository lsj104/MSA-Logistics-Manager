package com.team12.hub.hubPath.dto;

import com.team12.hub.hub.domain.Hub;
import com.team12.hub.hubPath.domain.HubPath;
import com.team12.hub.hubPath.repository.HubPathRepository;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class HubPathResponseDto {

    private UUID id;
    private UUID fromHubId;  // 출발 허브 ID
    private UUID toHubId;  // 도착 허브 ID
    private int distance; // 허브 간 거리
    private int duration;  // 소요시간

    public HubPathResponseDto(HubPath hubPath){
        this.id = hubPath.getId();
        this.fromHubId = hubPath.getFromHub().getId();
        this.toHubId = hubPath.getToHub().getId();
        this.distance = hubPath.getDistance();
        this.duration = hubPath.getDuration();
    }


}
