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
    private Long userId;
    private String username;
    private String name;
    private String slackEmail;
    private UserRoleEnum role;
    private boolean isConfirmed;

    public UserDataDto(User user) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.name = user.getName();
        this.slackEmail = user.getSlackEmail();
        this.role = user.getUserRoleEnum();
        this.isConfirmed = user.isConfirmed();
    }
}
