package com.team12.hub.hubPath.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class HubPathCreateRequestDto {
    private UUID fromHubId;
    private UUID toHubId;
}
