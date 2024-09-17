package com.team12.user.domain;

import com.team12.user.dto.UserRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "P_USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String slackEmail;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING) //enumType 이름 그대로 db저장
    private UserRoleEnum userRoleEnum;

    @Column(nullable = false)
    private boolean isConfirmed;

    public User(UserRequestDto signUpReqeustDto, String password) {
        this.username = signUpReqeustDto.getUsername();
        this.password = password;
        this.slackEmail = signUpReqeustDto.getSlackEmail();
        this.name = signUpReqeustDto.getName();
        this.userRoleEnum = signUpReqeustDto.getUserRoleEnum();

    }

//    public User(UserApproveRequestDto userApproveRequestDto) {
//        this.isConfirmed = userApproveRequestDto.isConfirmed();
//    }
}
