package com.team12.user.repository;

import com.team12.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);

    Page<User> findByNameContainingOrUsernameContainingOrSlackEmailContaining(String name, String username, String slackEmail, Pageable pageable);
}
