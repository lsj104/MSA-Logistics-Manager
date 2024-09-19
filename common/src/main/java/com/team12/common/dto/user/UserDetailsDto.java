package com.team12.common.dto.user;

import com.team12.common.auth.UserRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsDto {
    private Long userId;
    private String username;
    private String password;
    private UserRoleEnum role;

}
