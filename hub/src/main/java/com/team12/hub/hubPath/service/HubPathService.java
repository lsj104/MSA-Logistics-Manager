package com.team12.hub.hubPath.service;

import com.team12.hub.hub.domain.Hub;
import com.team12.hub.hub.repository.HubRepository;
import com.team12.hub.hubPath.domain.HubPath;
import com.team12.hub.hubPath.domain.HubPathSpecification;
import com.team12.hub.hubPath.dto.HubPathRequestDto;
import com.team12.hub.hubPath.dto.HubPathResponseDto;
import com.team12.hub.hubPath.dto.HubPathSearchRequestDto;
import com.team12.hub.hubPath.repository.HubPathRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class HubPathService {


    private final HubPathRepository hubPathRepository;
    private final HubRepository hubRepository;
    // 캐시 이름을 'hubPaths'로 설정
    private static final String CACHE_NAME = "hubPaths";

    @Transactional
    public HubPathResponseDto createHubPath(HubPathRequestDto hubPathRequestDto) {
        Hub fromHub = hubRepository.findByIdAndIsDeleted(hubPathRequestDto.getFromHubId(), false)
                .orElseThrow(() -> new IllegalArgumentException("해당 출발 허브가 없습니다."));
        Hub toHub = hubRepository.findByIdAndIsDeleted(hubPathRequestDto.getToHubId(), false)
                .orElseThrow(() -> new IllegalArgumentException("해당 도착 허브가 없습니다."));

        HubPath hubPath = new HubPath(UUID.randomUUID(), fromHub, toHub, hubPathRequestDto.getDuration(), false);
        hubPathRepository.save(hubPath);
        HubPathResponseDto hubPathResponseDto = new HubPathResponseDto(hubPath);
        return hubPathResponseDto;
    }
    @Transactional
    public HubPath updateHubPath(UUID hubPathId, HubPathRequestDto hubPathRequestDto) {
        Hub fromHub = hubRepository.findByIdAndIsDeleted(hubPathRequestDto.getFromHubId(), false)
                .orElseThrow(() -> new IllegalArgumentException("해당 출발 허브가 없습니다."));
        Hub toHub = hubRepository.findByIdAndIsDeleted(hubPathRequestDto.getToHubId(), false)
                .orElseThrow(() -> new IllegalArgumentException("해당 도착 허브가 없습니다."));

        HubPath hubPath = new HubPath(hubPathId, fromHub, toHub, hubPathRequestDto.getDuration(), false);
        return hubPathRepository.save(hubPath);
    }


    @Transactional
    public UUID deleteHubPath(UUID hubPathId) {

        HubPath hubPath = hubPathRepository.findByIdAndIsDeleted(hubPathId, false)
                .orElseThrow(() -> new IllegalArgumentException("해당 허브 간 이동을 찾을 수 없습니다."));
        hubPath.setIsDeleted(true);
        hubPath.setDeletedAt(LocalDateTime.now());
        hubPath.setDeletedBy(0L);
        hubPathRepository.save(hubPath);
        return hubPathId;
    }

    @Transactional
    public HubPath getHubPath(UUID hubPathId) {
        HubPath hubPath = hubPathRepository.findByIdAndIsDeleted(hubPathId, false)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 허브 간 이동 정보가 없습니다."));
        return hubPath;
    }

    @Transactional
    public List<UUID> deleteHubPathsByHubId(UUID hubId) {
        List<UUID> hubPathIds = hubPathRepository.findHubPathsByHubId(hubId);
        System.out.println("======================"+hubPathIds+"======================");
        for (UUID hubPathId : hubPathIds) {
            System.out.println(hubPathId);
            HubPath hubPath = hubPathRepository.findByIdAndIsDeleted(hubPathId, false)
                    .orElseThrow(() -> new IllegalArgumentException("해당 간선을 찾을 수 없습니다."));
            hubPath.setIsDeleted(true);
            hubPath.setDeletedAt(LocalDateTime.now());
            hubPath.setDeletedBy(0L);
        }
        return hubPathIds;
    }

    public Page<HubPathResponseDto> getHubs(HubPathSearchRequestDto searchRequestDto, Pageable pageable) {
        Page<HubPath> hubPathPage = hubPathRepository.findAll(HubPathSpecification.searchWith(searchRequestDto), pageable);
        Page<HubPathResponseDto> hubPathResponseDtoPage = hubPathPage.map(hubPath -> new HubPathResponseDto(hubPath));
        return hubPathResponseDtoPage;
    }
}
