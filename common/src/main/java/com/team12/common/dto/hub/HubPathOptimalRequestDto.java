package com.team12.common.dto.hub;

import lombok.Data;

import java.util.UUID;

@Data
public class HubPathOptimalRequestDto {
    private UUID departureHubID;
    private UUID arrivalHubID;
}
