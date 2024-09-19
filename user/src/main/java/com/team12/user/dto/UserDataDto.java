package com.team12.user.dto;

import com.team12.common.auth.UserRoleEnum;
import com.team12.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDataDto {
    private String username;
    private String name;
    private String slackEmail;
    private UserRoleEnum role;

    public UserDataDto(User user) {
        this.username = user.getUsername();
        this.name = user.getName();
        this.slackEmail = user.getSlackEmail();
        this.role = user.getUserRoleEnum();
    }
}
