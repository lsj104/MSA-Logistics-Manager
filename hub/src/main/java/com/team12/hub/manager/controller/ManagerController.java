package com.team12.hub.manager.controller;

import com.team12.hub.manager.dto.ManagerRequestDto;
import com.team12.hub.manager.dto.ManagerResponseDto;
import com.team12.hub.manager.dto.ManagerSearchRequestDto;
import com.team12.hub.manager.repository.ManagerRepositoy;
import com.team12.hub.manager.service.ManagerService;
import com.team12.hub.manager.type.ManagerType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/managers")
public class ManagerController {

    private final ManagerService managerService;
    private final ManagerRepositoy managerRepositoy;

    @PostMapping
    public ManagerResponseDto createManager(@RequestBody ManagerRequestDto managerRequestDto){
        return managerService.createManager(managerRequestDto);
    }

    @PutMapping("/{managerId}")
    public ManagerResponseDto updateManager(@PathVariable Long managerId, @RequestBody ManagerRequestDto managerRequestDto) {
        try {
            return managerService.updateManager(managerId, managerRequestDto);

        } catch(Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    @DeleteMapping("/{managerId}")
    public Long deleteManager(@PathVariable Long managerId){
        return managerService.deleteManager(managerId);
    }

    @GetMapping("/{managerId}")
    public ManagerResponseDto getManager(@PathVariable Long managerId){
        return managerService.getManager(managerId);
    }

    @GetMapping
    public Page<ManagerResponseDto> getManagers(@RequestParam(required = false) Long managerId,
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
        Page<ManagerResponseDto> managerResponseDtoPage = managerService.getManagers(searchRequestDto, pageable);


        return managerResponseDtoPage;
    }
}
