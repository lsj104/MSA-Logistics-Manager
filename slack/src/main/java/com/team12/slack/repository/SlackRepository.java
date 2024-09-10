package com.team12.slack.repository;

import com.team12.slack.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SlackRepository extends JpaRepository<Message, Integer> {
}
