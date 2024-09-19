package com.team12.user.service;

import com.team12.common.customPage.CustomPageResponse;
import com.team12.common.exception.BusinessLogicException;
import com.team12.common.exception.ExceptionCode;
import com.team12.common.exception.test.BusinessException;
import com.team12.user.domain.User;
import com.team12.user.dto.*;
import com.team12.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.config.JpaRepositoryNameSpaceHandler;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //회원가입
    public UserResponseDto signUp(UserSignUpRequestDto signUpRequestDto) {
        // username 중복 확인 Todo : Exception
        userRepository.findByUsername(signUpRequestDto.getUsername());
        // Todo : password 인코딩 (객체 생성 전)
        String encodedPassword = passwordEncoder.encode(signUpRequestDto.getPassword());
        // Todo : entity 변환
        User user = new User(signUpRequestDto, encodedPassword);
        // db 저장
        userRepository.save(user);
        // dto 변환
        UserDataDto userDataDto = new UserDataDto(user);
        // ResponseDto
        return new UserResponseDto<UserDataDto>(201, "회원가입 성공", userDataDto);
    }

    //관리자 : 유저 가입 승인
    public UserResponseForRegisterDto approve(Long userId, boolean isConfirmed) {
        //repository 찾기
        User user = userRepository.findById(userId);
        // true로 변경
        user.setConfirmed(isConfirmed);
        //User 승인 여부 db 저장
        userRepository.save(user);
        //dto로 변환
        UserDataForRegisterDto userDataForRegisterDto = new UserDataForRegisterDto(user);
        return new UserResponseForRegisterDto<UserDataForRegisterDto>(200, "유저 생성 승인 성공", userDataForRegisterDto);
    }

    // 유저 : 개인의 상세 정보 조회
    public UserResponseDto getUserDetail(Long userId) {
        // repository 찾기
        User user = userRepository.findById(userId);
        // dto 변환
        UserDataDto userDataDto = new UserDataDto(user);
        return new UserResponseDto<UserDataDto>(200, "유저의 개인 상세 정보 조회 성공", userDataDto);
    }

    // 관리자 : 유저의 상세 정보 조회
    public UserResponseForRegisterDto getUserDetailForRegister(Long userId) {
        //repository 찾기
        User user = userRepository.findById(userId);
        //dto 변환
        UserDataForRegisterDto userDataForRegisterDto = new UserDataForRegisterDto(user);
        return new UserResponseForRegisterDto<UserDataForRegisterDto>(200, "유저 상세 정보 조회 성공", userDataForRegisterDto);
    }

    // 관리자 : 유저 리스트 조회
    public CustomPageResponse<UserDataForRegisterDto> getUsers(int page, int size, String sort) {
        //sort
        Sort sortBy = Sort.by(Sort.Direction.DESC, sort);
        Pageable pageable = PageRequest.of(page, size, sortBy);
        // repository 검색
        Page<User> userList = userRepository.findAll(pageable);
        // Dto 변환
        Page<UserDataForRegisterDto> dtoPage = userList.map(UserDataForRegisterDto:: new);
        return new CustomPageResponse<>(dtoPage);
    }

    // 관리자 : 유저 검색
    public CustomPageResponse<UserDataForRegisterDto> searchUsers(
            String searchText, int page, int size, String sort) {
        //sort
        Sort sortBy = Sort.by(Sort.Direction.DESC, sort);
        Pageable pageable = PageRequest.of(page, size, sortBy);

        Page<User> searchUsers = userRepository.search(searchText, pageable);
        // to Dto
        Page<UserDataForRegisterDto> userList = searchUsers.map(UserDataForRegisterDto::new);
        return new CustomPageResponse<>(userList);

    }

    //관리자 : 유저 수정
    public UserResponseForRegisterDto patchUser(Long userId, UserPatchRequestForRegisterDto patchDto) {
        //repository 검색
        User user = userRepository.findById(userId);
        //수정 및 유효성 검사(unique 값)
        if(patchDto.getName() == null || patchDto.getUsername() == null ||
        patchDto.getSlackEmail() == null || patchDto.getUserRoleEnum() == null) {
            throw new BusinessLogicException(ExceptionCode.NOT_PROVIDE_LANGUAGE);
        }
        user.setName(patchDto.getName());
        user.setUsername(patchDto.getUsername());
        user.setSlackEmail(patchDto.getSlackEmail());
        user.setUserRoleEnum(patchDto.getUserRoleEnum());
        user.setConfirmed(patchDto.isConfirmed());
        //db 저장
        userRepository.save(user);
        //to dto
        UserDataForRegisterDto patchedUserDto = new UserDataForRegisterDto(user);
        //return
        return new UserResponseForRegisterDto<> (200, "유저 정보 수정 완료", patchedUserDto);
    }

    //관리자 : 유저 삭제
    public void deleteUser(Long userId) {
        //repository 찾기
        User user = userRepository.findById(userId);
        //논리적 삭제 -> isDeleted = true
        user.setIsDeleted(true);
        //db 저장
        userRepository.save(user);
    }
}
