package com.team12.user.controller;

import com.team12.user.dto.UserDataForRegisterDto;
import com.team12.user.dto.UserRequestDto;
import com.team12.user.dto.UserResponseDto;
import com.team12.user.dto.UserResponseForRegisterDto;
import com.team12.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //회원가입
    @PostMapping("/sign-up")
    public ResponseEntity signUp(@RequestBody UserRequestDto userRequestDto) throws Exception {
        UserResponseDto userResponseDto = userService.signUp(userRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDto);
    }

    //관리자 : 유저 가입 승인
    @PatchMapping("/{userId}/approve")
    public ResponseEntity approve(@PathVariable(name = "userId") Long userId,
                                  @RequestParam(name = "isConfirmed") boolean isConfirmed) {
        UserResponseForRegisterDto userResponseForRegisterDto = userService.approve(userId, isConfirmed);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userResponseForRegisterDto);
    }
    //유저 : 개인 상세 정보 조회
    @GetMapping
    public ResponseEntity getUserDetail() {
        //Todo : header 토큰 받아서 처리하기
        Long userId = 1L;
        UserResponseDto userResponseDto = userService.getUserDetail(userId);
        return ResponseEntity.ok(userResponseDto);
    }

    //관리자 : 유저 상세 정보 조회
    @GetMapping("/{userId}")
    public ResponseEntity getUserDetailForRegister(@PathVariable("userId") Long userId) {
        UserResponseForRegisterDto userResponseForRegisterDto = userService.getUserDetailForRegister(userId);
        return ResponseEntity.ok(userResponseForRegisterDto);
    }
    //관리자 : 유저 리스트 조회

    //관리자 : 유저 검색

    //관리자 : 유저 정보 수정

    //관리자 : 유저 삭제


}
