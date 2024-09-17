package com.team12.hub.hub.service;

import com.team12.hub.hub.domain.Hub;
import com.team12.hub.hub.domain.HubSpecification;
import com.team12.hub.hub.dto.HubRequestDto;
import com.team12.hub.hub.dto.HubResponseDto;
import com.team12.hub.hub.dto.HubSearchRequestDto;
import com.team12.hub.hub.repository.HubRepository;
import com.team12.hub.hubPath.service.HubPathService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

    public HubResponseDto updateHub(UUID hubId, HubRequestDto hubRequestDto) {
        Hub hub = hubRepository.findByIdAndIsDeleted(hubId,false)
                .orElseThrow(() -> new IllegalArgumentException(hubId + "해당 허브를 찾을 수 없습니다."));
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
    public UUID deleteHub(UUID hubId) {

        // 삭제될 허브와 연결된 모든 hubPath 논리적 삭제
        List<UUID> deletedHubPaths = hubPathService.deleteHubPathsByHubId(hubId);
        System.out.println(deletedHubPaths);

        // 허브 논리적 삭제
        Hub hub = hubRepository.findByIdAndIsDeleted(hubId, false)
                .orElseThrow(() -> new IllegalArgumentException("해당 허브를 찾을 수 없습니다."));
        hub.setIsDeleted(true);
        hub.setDeletedAt(LocalDateTime.now());
        hub.setDeletedBy(0L);
        hubRepository.save(hub);
        return hubId;
    }

    public HubResponseDto getHub(UUID hubId) {
        Hub hub = hubRepository.findByIdAndIsDeleted(hubId, false)
                .orElseThrow(() -> new IllegalArgumentException("해당 허브를 찾을 수 없습니다."));
        HubResponseDto hubResponseDto = new HubResponseDto(hub.getId(), hub.getName(), hub.getAddress(), hub.getLatitude(), hub.getLongitude());
        return hubResponseDto;
    }

    public Page<HubResponseDto> getHubs(HubSearchRequestDto searchRequestDto, Pageable pageable) {
        Page<Hub> hubPage = hubRepository.findAll(HubSpecification.searchWith(searchRequestDto), pageable);
        Page<HubResponseDto> hubResponseDtoPage = hubPage.map(hub -> new HubResponseDto(hub));
        return hubResponseDtoPage;
    }

    public UUID checkHub(UUID hubId) {
        Hub hub = hubRepository.findByIdAndIsDeleted(hubId, false)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 Hub 입니다."));
        return hub.getId();
    }
}
