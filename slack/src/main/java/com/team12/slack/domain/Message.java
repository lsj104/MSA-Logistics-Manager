package com.team12.slack.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "message", schema = "SLACK")
@Entity(name = "messages")
public class Message {
    // todo : auditEntity 상속
    // todo : 추후 유저 정보 받아오는 로직 추가
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int message_id;
//    @CollectionTable(name = "users", joinColumns = @JoinColumn(name = "user_id"))
//    @Column(name = "user_id")
//    private User user;
    private String email;
    private String content;
    private String send_at;

    public Message(String email, String content) {
        this.email = email;
        this.content = content;
        this.send_at = LocalDateTime.now().toString();
    }

//    public Message(User user, String content) {
//        this.user = user;
//        this.content = content;
//        this.send_at = LocalDateTime.now().toString();
//    }
}
