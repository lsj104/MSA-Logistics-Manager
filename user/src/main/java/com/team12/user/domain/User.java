package com.team12.user.domain;

import com.team12.common.audit.AuditingEntity;
import com.team12.common.enums.UserRoleEnum;
import com.team12.user.dto.UserSignUpRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "P_USER")
public class User extends AuditingEntity {

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

    public User(UserSignUpRequestDto signUpRequestDto, String password) {
        this.username = signUpRequestDto.getUsername();
        this.password = password;
        this.slackEmail = signUpRequestDto.getSlackEmail();
        this.name = signUpRequestDto.getName();
        this.userRoleEnum = signUpRequestDto.getUserRoleEnum();

    }

//    public User(UserApproveRequestDto userApproveRequestDto) {
//        this.isConfirmed = userApproveRequestDto.isConfirmed();
//    }
}
