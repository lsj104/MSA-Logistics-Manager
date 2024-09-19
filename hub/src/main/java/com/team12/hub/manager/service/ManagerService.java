package com.team12.hub.manager.service;

import com.team12.common.exception.BusinessLogicException;
import com.team12.common.exception.ExceptionCode;
import com.team12.hub.hub.domain.Hub;
import com.team12.hub.hub.repository.HubRepository;
import com.team12.hub.manager.domain.Manager;
import com.team12.hub.manager.domain.ManagerSpecification;
import com.team12.hub.manager.dto.ManagerRequestDto;
import com.team12.hub.manager.dto.ManagerResponseDto;
import com.team12.hub.manager.dto.ManagerSearchRequestDto;
import com.team12.hub.manager.repository.ManagerRepositoy;
import com.team12.hub.manager.type.ManagerType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ManagerService {

    private final ManagerRepositoy managerRepository;
    private final HubRepository hubRepository;

    public ManagerResponseDto createManager(ManagerRequestDto managerRequestDto, Long loginUserId) {
        /*(허브 별) 관리자 HUB_MANAGER 라면
        매니저가 해당 허브 소속 매니저인지 확인
        List<Manager> managers = managerRepository.findByHub(managerRequestDto)
        */
        /*(허브 별) 업체 배송 담당자 TO_COMPANY_DELIVERY 라면
        로그인 유저 ID가 userId와 동일한지 확인
         */

        Hub hub = hubRepository.findByIdAndIsDeleted(managerRequestDto.getHubId(), false)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.HUB_NOT_FOUND));
        Manager manager = new Manager(managerRequestDto.getUserId(), hub, managerRequestDto.getType());
        manager.setCreatedBy(loginUserId);
        managerRepository.save(manager);
        return new ManagerResponseDto(manager);
    }

    public ManagerResponseDto updateManager(Long managerId, ManagerRequestDto managerRequestDto, Long loginUserId) {
        /*(허브 별) 관리자 HUB_MANAGER 라면
        매니저가 해당 허브 소속 매니저인지 확인
        List<Manager> managers = managerRepository.findByHub(managerRequestDto)
        */
        /*(허브 별) 업체 배송 담당자 TO_COMPANY_DELIVERY 라면
        로그인 유저 ID가 managerId와 동일한지 확인
         */
        Manager manager = managerRepository.findByIdAndIsDeleted(managerId, false)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MANAGER_NOT_FOUND));
        if(managerRequestDto.getHubId() != null){
            Hub hub = hubRepository.findByIdAndIsDeleted(managerRequestDto.getHubId(), false)
                    .orElseThrow(() -> new BusinessLogicException(ExceptionCode.HUB_NOT_FOUND));
            manager.setHub(hub);
        }
        if(managerRequestDto.getType() != null){
            manager.setType(managerRequestDto.getType());
        }
        manager.setUpdatedBy(loginUserId);
        managerRepository.save(manager);
        ManagerResponseDto managerResponseDto =  new ManagerResponseDto(manager);
        return managerResponseDto;
    }

    public Long deleteManager(Long managerId, Long loginUserId) {
        Manager manager = managerRepository.findByIdAndIsDeleted(managerId, false)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MANAGER_NOT_FOUND));
        manager.setIsDeleted(true);
        manager.setDeletedAt(LocalDateTime.now());
        manager.setDeletedBy(loginUserId);
        managerRepository.save(manager);
        return managerId;
    }

    public ManagerResponseDto getManager(Long managerId) {
        Manager manager = managerRepository.findByIdAndIsDeleted(managerId, false)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MANAGER_NOT_FOUND));
        return new ManagerResponseDto(manager.getId(), manager.getHub().getId(), manager.getType());
    }

    public Page<ManagerResponseDto> getManagers(ManagerSearchRequestDto searchRequestDto, Pageable pageable) {
        Page<Manager> managerPage = managerRepository.findAll(ManagerSpecification.searchWith(searchRequestDto), pageable);
        return managerPage.map(ManagerResponseDto::new);
    }

    public List<ManagerResponseDto> getHubToHubManagers() {
        List<Manager> managers = managerRepository.findByType(ManagerType.HUB_TO_HUB_DELIVERY);
        return managers.stream().map(ManagerResponseDto::new).collect(Collectors.toList());
    }

    public List<ManagerResponseDto> getHubToCompanyManagers(UUID hubId) {
        Hub hub = hubRepository.findByIdAndIsDeleted(hubId, false)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 허브가 없습니다."));
        List<Manager> managers = managerRepository.findByHubAndType(hub, ManagerType.TO_COMPANY_DELIVERY);
        return managers.stream().map(ManagerResponseDto::new).collect(Collectors.toList());
    }

    public UUID findHubIdByUserId(Long userId) {
        Manager manager = managerRepository.findByIdAndIsDeleted(userId, false)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_MANAGER_NOT_FOUND));
        UUID hubId = manager.getHub().getId();
        return hubId;
    }
}
