package com.team12.common.dto.hub;

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

    public enum ManagerType {

        HUB_MANAGER,    //(허브 별) 관리자
        HUB_TO_HUB_DELIVERY, //(공통) 허브 이동 담당자
        TO_COMPANY_DELIVERY //(허브 별) 업체 배송 담당자
    }

}
