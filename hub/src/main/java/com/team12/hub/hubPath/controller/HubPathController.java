package com.team12.hub.hubPath.controller;

import com.team12.common.dto.hub.HubPathDetailsResponseDto;
import com.team12.common.dto.hub.HubPathOptimalRequestDto;
import com.team12.hub.hub.dto.HubResponseDto;
import com.team12.hub.hubPath.domain.HubPath;
import com.team12.hub.hubPath.dto.HubPathCreateRequestDto;
import com.team12.hub.hubPath.dto.HubPathResponseDto;
import com.team12.hub.hubPath.dto.HubPathSearchRequestDto;
import com.team12.hub.hubPath.dto.HubPathUpdateRequestDto;
import com.team12.hub.hubPath.service.HubPathService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/api/hub-paths")
@RestController
public class HubPathController {
    private final HubPathService hubPathService;

    @PostMapping
    public HubPathResponseDto createHubPath(@RequestBody HubPathCreateRequestDto hubPathCreateRequestDto) {
        return hubPathService.createHubPath(hubPathCreateRequestDto);
    }

    @PutMapping("/{hubPathId}")
    public HubPath updateHubPath(@PathVariable UUID hubPathId, @RequestBody HubPathUpdateRequestDto hubPathUpdateRequestDto) {
        return hubPathService.updateHubPath(hubPathId, hubPathUpdateRequestDto);
    }

    @DeleteMapping("/{hubPathId}")
    public UUID deleteHubPath(@PathVariable UUID hubPathId) {
        return hubPathService.deleteHubPath(hubPathId);
    }

    @GetMapping("/{hubPathId}")
    public HubPath getHubPath(@PathVariable UUID hubPathId) {
        return hubPathService.getHubPath(hubPathId);
    }

    @GetMapping
    public Page<HubPathResponseDto> getHubPaths(@RequestParam(required = false) UUID fromHubID,
                                                @RequestParam(required = false) UUID toHubID,
                                                @RequestParam(defaultValue = "1") int page,
                                                @RequestParam(defaultValue = "10") int size,
                                                @RequestParam(defaultValue = "fromHubId") String sort,
                                                @RequestParam(defaultValue = "asc") String direction
                                                ) {
        HubPathSearchRequestDto searchRequestDto = new HubPathSearchRequestDto(fromHubID, toHubID);
        // 정렬 순서 및 방향 설정
        Sort.Direction sortDirection = Sort.Direction.fromString(direction);
        Sort sortOption = Sort.by(sortDirection, sort);
        // size가 10, 20, 30이 아닌 경우 10으로 조정
        if (size != 10 && size != 20 && size != 30) {
            size = 10;
        }
        Pageable pageable = PageRequest.of(page - 1, size, sortOption);
        Page<HubPathResponseDto> hubPathResponsDtoPage = hubPathService.getHubs(searchRequestDto, pageable);
        return hubPathResponsDtoPage;
    }

    @GetMapping("/findOptimalPath")
    public List<HubPathDetailsResponseDto> findOptimalPath(@RequestBody HubPathOptimalRequestDto hubPathOptimalRequestDto) {
        return hubPathService.findOptimalPath(hubPathOptimalRequestDto.getDepartureHubID(), hubPathOptimalRequestDto.getArrivalHubID());
    }
}
