package com.team12.hub.hub.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class HubSearchRequestDto {
    private UUID id;
    private String name;
    private String address;
}
