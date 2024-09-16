package com.team12.common.dto.hub;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HubPathDetailsResponseDto {
    private UUID hubPathId;
    private UUID fromHubId;
    private UUID toHubId;
    private int distance;
    private int duration;
}
