package com.team12.common.dto.hub;

import lombok.Data;

import java.util.UUID;

@Data
public class HubPathDetailsResponseDto {
    private UUID hubPathId;
    private UUID fromHubId;
    private UUID toHubId;
    private double distance;
    private int duration;
}
