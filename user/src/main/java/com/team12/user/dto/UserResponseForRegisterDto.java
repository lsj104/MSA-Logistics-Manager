package com.team12.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseForRegisterDto<T> {
    private int resultCode;
    private String message;
    private UserDataForRegisterDto data;
}
