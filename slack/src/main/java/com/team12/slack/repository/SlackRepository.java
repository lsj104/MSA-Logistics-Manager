package com.team12.slack.repository;

import com.team12.slack.domain.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

@Repository
public interface SlackRepository extends JpaRepository<Message, UUID> {
    Page<Message> findByEmail(String email, Pageable pageable);
}
