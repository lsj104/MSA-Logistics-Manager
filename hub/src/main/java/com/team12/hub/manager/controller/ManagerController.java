package com.team12.hub.manager.controller;

import com.team12.hub.manager.dto.ManagerRequestDto;
import com.team12.hub.manager.dto.ManagerResponseDto;
import com.team12.hub.manager.service.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;



@RestController
@RequiredArgsConstructor
@RequestMapping("/api/managers")
public class ManagerController {

    private final ManagerService managerService;
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
}
