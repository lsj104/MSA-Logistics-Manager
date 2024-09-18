package com.team12.hub.manager.dto;

import com.team12.hub.manager.domain.Manager;
import com.team12.hub.manager.type.ManagerType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class ManagerResponseDto {
    private Long id;
    private UUID hubId;
    private ManagerType type;

    public ManagerResponseDto(Manager manager) {
        this.id = manager.getId();
        this.hubId = manager.getHub().getId();
        this.type = manager.getType();
    }
}
