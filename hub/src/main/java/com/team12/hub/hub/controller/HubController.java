package com.team12.hub.hub.controller;

import com.team12.hub.hub.dto.HubRequestDto;
import com.team12.hub.hub.dto.HubResponseDto;
import com.team12.hub.hub.dto.HubSearchRequestDto;
import com.team12.hub.hub.service.HubService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/api/hubs")
@RestController
public class HubController {
    private final HubService hubService;
    @PostMapping
    public void createHub(@RequestBody HubRequestDto hubRequestDto){
        hubService.createHub(hubRequestDto);
    }
    @PutMapping("/{hubId}")
    public void updateHub(@PathVariable UUID hubId, @RequestBody HubRequestDto hubRequestDto){
        hubService.updateHub(hubId, hubRequestDto);
    }
    @DeleteMapping("/{hubId}")
    public void deleteHub(@PathVariable UUID hubId){
        hubService.deleteHub(hubId);
    }
    @GetMapping("/{hubId}")
    public HubResponseDto getHub(@PathVariable UUID hubId){
        return hubService.getHub(hubId);
    }
    @GetMapping
    public Page<HubResponseDto> getHubs(@RequestParam(required = false) UUID id,
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
        Page<HubResponseDto> hubResponsDtoPage = hubService.getHubs(searchRequestDto, pageable);
        return hubResponsDtoPage;
    }
}
