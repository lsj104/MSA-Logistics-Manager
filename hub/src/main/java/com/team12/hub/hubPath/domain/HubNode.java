package com.team12.hub.hubPath.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@AllArgsConstructor
@Data
public class HubNode {
    private UUID hubId;
    private int duration;
}
