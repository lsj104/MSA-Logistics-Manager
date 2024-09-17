package com.team12.user.service;

import com.team12.common.exception.BusinessLogicException;
import com.team12.common.exception.ExceptionCode;
import com.team12.common.exception.test.BusinessException;
import com.team12.user.domain.User;
import com.team12.user.dto.UserDataDto;
import com.team12.user.dto.UserRequestDto;
import com.team12.user.dto.UserResponseDto;
import com.team12.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.team12.common.exception.test.ErrorCode.EXIST_PRODUCT;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //회원가입
    public UserResponseDto signUp(UserRequestDto signUpReqeustDto) {
        // username 중복 확인
        if(userRepository.findByUsername(signUpReqeustDto.getUsername()).isPresent()) {
            throw new BusinessLogicException(ExceptionCode.EXIST_PRODUCT);
        }
        // password 인코딩 (객체 생성 전)
        String encodedPassword = passwordEncoder.encode(signUpReqeustDto.getPassword());
        // entity 변환
        User user = new User(signUpReqeustDto, encodedPassword);
        // db 저장
        userRepository.save(user);
        // dto 변환
        UserDataDto userDataDto = new UserDataDto(user);
        // ResponseDto
        return new UserResponseDto<UserDataDto>(201, "회원가입 성공", userDataDto);
    }
}
