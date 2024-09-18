package com.team12.hub.hubPath.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class HubPathSearchRequestDto {
    private UUID fromHubId;
    private UUID toHubId;
}
