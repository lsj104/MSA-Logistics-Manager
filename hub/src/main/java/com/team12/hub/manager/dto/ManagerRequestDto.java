package com.team12.hub.manager.dto;

import com.team12.hub.manager.type.ManagerType;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
public class ManagerRequestDto {
    private UUID hubId;
    private ManagerType type;
}
