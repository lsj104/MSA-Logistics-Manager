package com.team12.user.dto;

import com.team12.user.domain.UserRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSignUpRequestDto {
    private String username;
    private String password;
    private String slackEmail;
    private String name;
    private UserRoleEnum userRoleEnum;
}
