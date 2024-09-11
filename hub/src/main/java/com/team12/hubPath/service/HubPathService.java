package com.team12.hubPath.service;

import com.team12.hub.domain.Hub;
import com.team12.hub.repository.HubRepository;
import com.team12.hubPath.domain.HubPath;
import com.team12.hubPath.dto.HubPathRequestDto;
import com.team12.hubPath.repository.HubPathRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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
    @CachePut(value = CACHE_NAME, key = "#hubPath.id")
    public HubPath createHubPath(HubPathRequestDto hubPathRequestDto) {
        Hub fromHub = hubRepository.findByIdAndIsDeleted(hubPathRequestDto.getFromHubId(), false)
                .orElseThrow(() -> new IllegalArgumentException("해당 출발 허브가 없습니다."));
        Hub toHub = hubRepository.findByIdAndIsDeleted(hubPathRequestDto.getToHubId(), false)
                .orElseThrow(() -> new IllegalArgumentException("해당 도착 허브가 없습니다."));

        HubPath hubPath = new HubPath(UUID.randomUUID(), fromHub, toHub, hubPathRequestDto.getDuration(), false);
        return hubPathRepository.save(hubPath);
    }
    @Transactional
    @CachePut(value = CACHE_NAME, key = "#hubPath.id")
    public HubPath updateHubPath(UUID hubPathId, HubPathRequestDto hubPathRequestDto) {
        Hub fromHub = hubRepository.findByIdAndIsDeleted(hubPathRequestDto.getFromHubId(), false)
                .orElseThrow(() -> new IllegalArgumentException("해당 출발 허브가 없습니다."));
        Hub toHub = hubRepository.findByIdAndIsDeleted(hubPathRequestDto.getToHubId(), false)
                .orElseThrow(() -> new IllegalArgumentException("해당 도착 허브가 없습니다."));

        HubPath hubPath = new HubPath(hubPathId, fromHub, toHub, hubPathRequestDto.getDuration(), false);
        return hubPathRepository.save(hubPath);
    }


    @Transactional
    @CacheEvict(value = CACHE_NAME, key = "#id")
    public UUID deleteHubPath(UUID hubPathId) {

        HubPath hubPath = hubPathRepository.findByIdAndIsDeleted(hubPathId, false)
                .orElseThrow(() -> new IllegalArgumentException("해당 허브 간 이동을 찾을 수 없습니다."));
        hubPath.setIsDeleted(true);
        hubPath.setDeletedAt(LocalDateTime.now());
        hubPath.setDeletedBy("loginUserIdx");
        hubPathRepository.save(hubPath);
        return hubPathId;
    }

    @Transactional
    @Cacheable(value = CACHE_NAME, key = "#id")
    public HubPath getHubPath(UUID hubPathId) {
        HubPath hubPath = hubPathRepository.findByIdAndIsDeleted(hubPathId, false)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 허브 간 이동 정보가 없습니다."));
        return hubPath;
    }

    public List<UUID> deleteHubPathsByHubId(UUID hubId) {
        List<UUID> hubPathIds = hubPathRepository.findHubPathsByHubId(hubId);
        for (UUID hubPathId : hubPathIds) {
            HubPath hubPath = hubPathRepository.findByIdAndIsDeleted(hubPathId, false)
                    .orElseThrow(() -> new IllegalArgumentException("해당 간선을 찾을 수 없습니다."));
            hubPath.setIsDeleted(true);
        }
        return hubPathIds;
    }
}
