package com.team12.hub.manager.controller;


import com.team12.common.enums.UserRoleEnum;
import com.team12.common.exception.BusinessLogicException;
import com.team12.common.exception.ExceptionCode;
import com.team12.common.exception.response.SuccessResponse;
import com.team12.hub.manager.dto.ManagerRequestDto;
import com.team12.hub.manager.dto.ManagerResponseDto;
import com.team12.hub.manager.dto.ManagerSearchRequestDto;
import com.team12.hub.manager.service.ManagerService;
import com.team12.hub.manager.type.ManagerType;
import com.team12.hub.util.SuccessMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/managers")
public class ManagerController {

    private final ManagerService managerService;

    @PostMapping
    public SuccessResponse<?> createManager(@RequestBody ManagerRequestDto managerRequestDto, @RequestHeader("X-User-Id") Long loginUserId){
        ManagerResponseDto createdManager = managerService.createManager(managerRequestDto, loginUserId);
        return SuccessResponse.success(SuccessMessage.CREATE_MANAGER.getHttpStatus().value(), SuccessMessage.CREATE_MANAGER.getMessage(), createdManager);
    }

    @PutMapping("/{managerId}")
    public SuccessResponse<?> updateManager(@PathVariable Long managerId, @RequestBody ManagerRequestDto managerRequestDto, @RequestHeader("X-User-Id") Long loginUserId) {
        ManagerResponseDto updatedManager = managerService.updateManager(managerId, managerRequestDto, loginUserId);
        return SuccessResponse.success(SuccessMessage.UPDATE_MANAGER.getHttpStatus().value(), SuccessMessage.UPDATE_MANAGER.getMessage(), updatedManager);
    }
    @DeleteMapping("/{managerId}")
    public SuccessResponse<?> deleteManager(@PathVariable Long managerId, @RequestHeader("X-User-Id") Long loginUserId){
        Long deletedManagerID =  managerService.deleteManager(managerId, loginUserId);
        return SuccessResponse.success(SuccessMessage.DELETE_MANAGER.getHttpStatus().value(), SuccessMessage.DELETE_MANAGER.getMessage(), deletedManagerID);
    }

    @GetMapping("/{managerId}")
    public SuccessResponse<?> getManager(@RequestHeader("X-User-Id") Long loginUserId,
                                         @RequestHeader("X-User-Role") String userRole,
                                         @PathVariable Long managerId){
        ManagerResponseDto managerResponseDto = managerService.getManager(managerId);
        if (UserRoleEnum.Authority.HUB_MANAGER.equals(userRole)) {
            // 현재 userId로 해당 사용자의 허브 ID 조회
            UUID userHubId = managerService.findHubIdByUserId(loginUserId);
            if (!userHubId.equals(managerResponseDto.getHubId())) {
                throw new BusinessLogicException(ExceptionCode.NOT_YOUR_HUB);
            }
        }

        return SuccessResponse.success(SuccessMessage.GET_MANAGER.getHttpStatus().value(), SuccessMessage.GET_MANAGER.getMessage(), managerResponseDto);
    }

    @GetMapping
    public SuccessResponse<?> getManagers(@RequestHeader("X-User-Id") Long userId,
                                          @RequestHeader("X-User-Role") String userRole,
                                          @RequestParam(required = false) Long managerId,
                                          @RequestParam(required = false) UUID hubId,
                                          @RequestParam(required = false) ManagerType type,
                                          @RequestParam(required = false, defaultValue = "1") int page,
                                          @RequestParam(required = false, defaultValue = "10") int size,
                                          @RequestParam(required = false, defaultValue = "id") String sort,
                                          @RequestParam(required = false, defaultValue = "ASC") String direction){
        ManagerSearchRequestDto searchRequestDto = new ManagerSearchRequestDto(managerId, hubId, type);
        // 정렬 순서 및 방향 설정
        Sort.Direction sortDirection = Sort.Direction.fromString(direction);
        Sort sortOption = Sort.by(sortDirection, sort);
        // size가 10, 20, 30이 아닌 경우 10으로 조정
        if (size != 10 && size != 20 && size != 30) {
            size = 10;
        }
        Pageable pageable = PageRequest.of(page - 1, size, sortOption);
        // userRole에 따라 조건 추가
        if (UserRoleEnum.Authority.HUB_MANAGER.equals(userRole)) {
            // 현재 userId로 해당 사용자의 허브 ID 조회
            UUID userHubId = managerService.findHubIdByUserId(userId);
            // 허브 매니저라면 해당 허브에 속한 매니저만 조회하도록 검색 조건 설정
            searchRequestDto.setHubId(userHubId);
        }
        Page<ManagerResponseDto> managerResponseDtoPage = managerService.getManagers(searchRequestDto, pageable);
        return SuccessResponse.success(SuccessMessage.GET_MANAGERS.getHttpStatus().value(), SuccessMessage.GET_MANAGERS.getMessage(), managerResponseDtoPage);
    }

    @GetMapping("/hub-to-hub")
    public List<ManagerResponseDto> getHubToHubManagers() {
        return managerService.getHubToHubManagers();
    }

    @GetMapping("/hub-to-company")
    public List<ManagerResponseDto> getHubToCompanyManagers(@RequestParam UUID hubId) {
        return managerService.getHubToCompanyManagers(hubId);
    }
}
