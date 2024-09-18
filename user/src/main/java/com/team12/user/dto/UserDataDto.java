package com.team12.user.dto;

import com.team12.user.domain.User;
import com.team12.user.domain.UserRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
