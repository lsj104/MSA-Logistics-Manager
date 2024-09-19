package com.team12.hub.hub.service;

import com.team12.common.exception.BusinessLogicException;
import com.team12.common.exception.ExceptionCode;
import com.team12.hub.hub.domain.Hub;
import com.team12.hub.hub.domain.HubSpecification;
import com.team12.hub.hub.dto.HubRequestDto;
import com.team12.hub.hub.dto.HubResponseDto;
import com.team12.hub.hub.dto.HubSearchRequestDto;
import com.team12.hub.hub.repository.HubRepository;
import com.team12.hub.hubPath.service.HubPathService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class HubService {
    private final HubRepository hubRepository;
    private final HubPathService hubPathService;
    private final KakaoMapService kakaoMapService;

    @CachePut(value = "hub", key = "#result.id")
    @CacheEvict(value = "hubAll", allEntries = true)
    public HubResponseDto createHub(HubRequestDto hubRequestDto) {
        // 새 허브가 등록될 때, Google Map API와 연동해 위도, 경도 받아오기
        List<String> latitudeAndLongitude = kakaoMapService.getLatLongFromAddress(hubRequestDto.getAddress());

        Hub hub = new Hub(
                UUID.randomUUID()
                , hubRequestDto.getName()
                , hubRequestDto.getAddress()
                , latitudeAndLongitude.get(0)
                , latitudeAndLongitude.get(1)
                , false);
        hubRepository.save(hub);
        return new HubResponseDto(hub);
    }

    @CacheEvict(value = {"hub", "hubAll"}, allEntries = true)
    @CachePut(value = "hub", key = "#hubId")
    public HubResponseDto updateHub(UUID hubId, HubRequestDto hubRequestDto) {
        Hub hub = hubRepository.findByIdAndIsDeleted(hubId,false)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.HUB_NOT_FOUND));
        if(hubRequestDto.getName() != null){
            hub.setName(hubRequestDto.getName());
        }
        if(hubRequestDto.getAddress() != null){
            hub.setAddress(hubRequestDto.getAddress());
        }
        hubRepository.save(hub);
        return new HubResponseDto(hub);
    }

    // 허브 삭제 메서드 (허브 삭제 시 HubPath도 삭제)
    @Transactional
    @CacheEvict(value = {"hub", "hubAll"}, allEntries = true)
    public UUID deleteHub(UUID hubId) {

        // 삭제될 허브와 연결된 모든 hubPath 논리적 삭제
        List<UUID> deletedHubPaths = hubPathService.deleteHubPathsByHubId(hubId);
        System.out.println(deletedHubPaths);

        // 허브 논리적 삭제
        Hub hub = hubRepository.findByIdAndIsDeleted(hubId, false)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.HUB_NOT_FOUND));
        hub.setIsDeleted(true);
        hub.setDeletedAt(LocalDateTime.now());
        hub.setDeletedBy(0L);
        hubRepository.save(hub);
        return hubId;
    }
    @Transactional
    @Cacheable(value = "hub", key = "#hubId")
    public HubResponseDto getHub(UUID hubId) {
        Hub hub = hubRepository.findByIdAndIsDeleted(hubId, false)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.HUB_NOT_FOUND));
        HubResponseDto hubResponseDto = new HubResponseDto(hub.getId(), hub.getName(), hub.getAddress(), hub.getLatitude(), hub.getLongitude());
        return hubResponseDto;
    }

    // 캐시 저장: List<HubResponseDto> 형태로 저장
    @Cacheable(value = "hubAll", key = "#searchRequestDto")
    public List<HubResponseDto> getHubs(HubSearchRequestDto searchRequestDto, Pageable pageable) {
        // Page에서 List로 변환
        Page<Hub> hubPage = hubRepository.findAll(HubSpecification.searchWith(searchRequestDto), pageable);
        List<HubResponseDto> hubResponseDtoList = hubPage.map(hub -> new HubResponseDto(hub)).getContent();

        // 캐시에 List 형태로 저장
        return hubResponseDtoList;
    }

    // 캐시된 List<HubResponseDto>를 다시 Page로 변환하는 메서드 (totalElements 사용하지 않음)
    public Page<HubResponseDto> convertListToPage(List<HubResponseDto> hubResponseDtoList, Pageable pageable) {
        return new PageImpl<>(hubResponseDtoList, pageable, hubResponseDtoList.size());
    }

    public UUID checkHub(UUID hubId) {
        Hub hub = hubRepository.findByIdAndIsDeleted(hubId, false)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.HUB_NOT_FOUND));
        return hub.getId();
    }
}
