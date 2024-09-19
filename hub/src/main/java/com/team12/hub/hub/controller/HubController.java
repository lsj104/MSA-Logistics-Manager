package com.team12.hub.hub.controller;

import com.team12.common.enums.UserRoleEnum;
import com.team12.common.exception.response.SuccessResponse;
import com.team12.hub.hub.dto.HubRequestDto;
import com.team12.hub.hub.dto.HubResponseDto;
import com.team12.hub.hub.dto.HubSearchRequestDto;
import com.team12.hub.hub.service.HubService;
import com.team12.hub.util.SuccessMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/api/hubs")
@RestController
public class HubController {
    private final HubService hubService;

    @PostMapping
    public SuccessResponse<?> createHub(@RequestBody HubRequestDto hubRequestDto, @RequestHeader("X-User-Id") Long loginUserId){
        HubResponseDto createdHub = hubService.createHub(hubRequestDto, loginUserId);
        return SuccessResponse.success(SuccessMessage.CREATE_HUB.getHttpStatus().value(), SuccessMessage.CREATE_HUB.getMessage(), createdHub);
    }
    @PutMapping("/{hubId}")
    public SuccessResponse<?> updateHub(@PathVariable UUID hubId, @RequestBody HubRequestDto hubRequestDto, @RequestHeader("X-User-Id") Long loginUserId){
        HubResponseDto updatedHub = hubService.updateHub(hubId, hubRequestDto, loginUserId);
        return SuccessResponse.success(SuccessMessage.UPDATE_HUB.getHttpStatus().value(), SuccessMessage.UPDATE_HUB.getMessage(), updatedHub);
    }
    @DeleteMapping("/{hubId}")
    public SuccessResponse<?> deleteHub(@PathVariable UUID hubId, @RequestHeader("X-User-Id") Long loginUserId){

        UUID deletedHubId = hubService.deleteHub(hubId, loginUserId);
        return SuccessResponse.success(SuccessMessage.DELETE_HUB.getHttpStatus().value(), SuccessMessage.DELETE_HUB.getMessage(), deletedHubId);
    }

    @GetMapping("/{hubId}")
    public SuccessResponse<?> getHub(@PathVariable UUID hubId){

        HubResponseDto hubResponseDto = hubService.getHub(hubId);
        return SuccessResponse.success(SuccessMessage.GET_HUB.getHttpStatus().value(), SuccessMessage.GET_HUB.getMessage(), hubResponseDto);
    }
    @GetMapping
    public SuccessResponse<?> getHubs(@RequestParam(required = false) UUID id,
                                        @RequestParam(required = false) String name,
                                        @RequestParam(required = false) String address,
                                        @RequestParam(value = "page", defaultValue = "1") int page,
                                        @RequestParam(value = "size", defaultValue = "10") int size,
                                        @RequestParam(value = "sort", defaultValue = "name") String sort,
                                        @RequestParam(value = "direction", defaultValue = "asc") String direction
                                        ){
        HubSearchRequestDto searchRequestDto = new HubSearchRequestDto(id, name, address);
        // 정렬 순서 및 방향 설정
        Sort.Direction sortDirection = Sort.Direction.fromString(direction);
        Sort sortOption = Sort.by(sortDirection, sort);
        // size가 10, 20, 30이 아닌 경우 10으로 조정
        if (size != 10 && size != 20 && size != 30) {
            size = 10;
        }
        Pageable pageable = PageRequest.of(page - 1, size, sortOption);
        // 캐시에서 List<HubResponseDto>를 가져온 후 Page로 변환
        List<HubResponseDto> cachedHubs = hubService.getHubs(searchRequestDto, pageable);

        // List를 다시 Page로 변환 (totalElements 사용하지 않음)
        Page<HubResponseDto> hubResponsDtoPage = hubService.convertListToPage(cachedHubs, pageable);


        return SuccessResponse.success(SuccessMessage.GET_HUBS.getHttpStatus().value(), SuccessMessage.GET_HUBS.getMessage(), hubResponsDtoPage);
    }

    @GetMapping("/{hubId}/check")
    public SuccessResponse<?> checkHub(@PathVariable UUID hubId){
        UUID checkedHubId = hubService.checkHub(hubId);
        return SuccessResponse.success(SuccessMessage.CHECK_HUB.getHttpStatus().value(), SuccessMessage.CHECK_HUB.getMessage(), checkedHubId);
    }
//    @GetMapping("/hub-paths-recommend-from-AI-by-all-hubs")
//    public String hubPathsRecommendFromAIByAllHubs(){
//        return hubService.hubPathsRecommendFromAIByAllHubs();
//    }

}
