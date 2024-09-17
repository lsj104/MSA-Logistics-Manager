package com.team12.user.service;

import com.team12.common.exception.BusinessLogicException;
import com.team12.common.exception.ExceptionCode;
import com.team12.user.domain.User;
import com.team12.user.dto.UserDataDto;
import com.team12.user.dto.UserRequestDto;
import com.team12.user.dto.UserResponseDto;
import com.team12.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    //private final PasswordEncoder passwordEncoder;

    //회원가입
    public UserResponseDto signUp(UserRequestDto signUpRequestDto) {
        // username 중복 확인 Todo : Exception
        if(userRepository.findByUsername(signUpRequestDto.getUsername()).isPresent()) {
            throw new BusinessLogicException(ExceptionCode.EXIST_PRODUCT);
        }
        // Todo : password 인코딩 (객체 생성 전)
        //String encodedPassword = passwordEncoder.encode(signUpRequestDto.getPassword());
        // Todo : entity 변환
        User user = new User(signUpRequestDto, "1234");
        // db 저장
        userRepository.save(user);
        // dto 변환
        UserDataDto userDataDto = new UserDataDto(user);
        // ResponseDto
        return new UserResponseDto<UserDataDto>(201, "회원가입 성공", userDataDto);
    }

    //관리자 : 유저 가입 승인
    public UserResponseDto approve(Long userId, boolean isConfirmed) {
        //repository 찾기
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.NOT_EXIST_USER));
        // true로 변경
        user.setConfirmed(isConfirmed);
        //User 승인 여부 db 저장
        userRepository.save(user);
        //dto로 변환
        UserDataDto userDataDto = new UserDataDto(user);
        return new UserResponseDto<UserDataDto>(200, "유저 생성 승인 성공", userDataDto);
    }
}
