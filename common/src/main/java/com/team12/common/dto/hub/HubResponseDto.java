package com.team12.common.dto.hub;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
@Data
public class HubResponseDto {
    private UUID id;
    private String name;
    private String address;
    private String latitude;
    private String longitude;

}
