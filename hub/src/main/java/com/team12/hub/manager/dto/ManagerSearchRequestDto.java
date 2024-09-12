package com.team12.hub.manager.dto;

import com.team12.hub.manager.type.ManagerType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class ManagerSearchRequestDto {
    private Long managerId;
    private UUID hubId;
    private ManagerType type;
}
