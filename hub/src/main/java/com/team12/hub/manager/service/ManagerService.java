package com.team12.hub.manager.service;

import com.team12.hub.hub.domain.Hub;
import com.team12.hub.hub.repository.HubRepository;
import com.team12.hub.manager.domain.Manager;
import com.team12.hub.manager.dto.ManagerRequestDto;
import com.team12.hub.manager.dto.ManagerResponseDto;
import com.team12.hub.manager.repository.ManagerRepositoy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@RequiredArgsConstructor
@Service
public class ManagerService {

    private final ManagerRepositoy managerRepository;
    private final HubRepository hubRepository;

    public ManagerResponseDto createManager(ManagerRequestDto managerRequestDto) {
        /*(허브 별) 관리자 HUB_MANAGER 라면
        매니저가 해당 허브 소속 매니저인지 확인
        List<Manager> managers = managerRepository.findByHub(managerRequestDto)
        */
        /*(허브 별) 업체 배송 담당자 TO_COMPANY_DELIVERY 라면
        로그인 유저 ID가 userId와 동일한지 확인
         */
        Hub hub = hubRepository.findByIdAndIsDeleted(managerRequestDto.getHubId(), false)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 허브를 찾을 수 없습니다."));
        Manager manager = new Manager(1357L, hub, managerRequestDto.getType());
        managerRepository.save(manager);
        return new ManagerResponseDto(manager);
    }

    public ManagerResponseDto updateManager(Long managerId, ManagerRequestDto managerRequestDto) {
        /*(허브 별) 관리자 HUB_MANAGER 라면
        매니저가 해당 허브 소속 매니저인지 확인
        List<Manager> managers = managerRepository.findByHub(managerRequestDto)
        */
        /*(허브 별) 업체 배송 담당자 TO_COMPANY_DELIVERY 라면
        로그인 유저 ID가 managerId와 동일한지 확인
         */
        Manager manager = managerRepository.findByIdAndIsDeleted(managerId, false)
                .orElseThrow(() -> new IllegalArgumentException(managerId + " ID를 찾을 수 없습니다."));
        if(managerRequestDto.getHubId() != null){
            Hub hub = hubRepository.findByIdAndIsDeleted(managerRequestDto.getHubId(), false)
                    .orElseThrow(() -> new IllegalArgumentException("해당하는 허브가 없습니다."));
            manager.setHub(hub);
        }
        if(managerRequestDto.getType() != null){
            manager.setType(managerRequestDto.getType());
        }
        managerRepository.save(manager);
        ManagerResponseDto managerResponseDto =  new ManagerResponseDto(manager);
        return managerResponseDto;
    }

    public Long deleteManager(Long managerId) {
        Manager manager = managerRepository.findByIdAndIsDeleted(managerId, false)
                .orElseThrow(() -> new IllegalArgumentException(managerId + " ID를 찾을 수 없습니다."));
        manager.setIsDeleted(true);
        managerRepository.save(manager);
        return managerId;
    }

    public ManagerResponseDto getManager(Long managerId) {
        Manager manager =  managerRepository.findByIdAndIsDeleted(managerId, false)
                .orElseThrow(() -> new IllegalArgumentException(managerId + " ID를 찾을 수 없습니다."));
        return new ManagerResponseDto(manager.getId(), manager.getHub().getId(), manager.getType());
    }
}
