package com.team12.user.service;

import com.team12.common.dto.user.UserDetailsDto;
import com.team12.user.domain.User;
import com.team12.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientService {

    private final UserRepository userRepository;

    public UserDetailsDto getUserDetails(String username) {
        //repository 조회
        try {
            User user = userRepository.findByUsernameForAuth(username);

        //to dto
        UserDetailsDto detailsDto = new UserDetailsDto();
        detailsDto.setUserId(user.getUserId());
        detailsDto.setUsername(user.getUsername());
        detailsDto.setPassword(user.getPassword());
        detailsDto.setRole(user.getUserRoleEnum());
        return detailsDto;
        } catch (Exception e ) {
            log.error(e.getMessage());
            return null;
        }
    }

}
