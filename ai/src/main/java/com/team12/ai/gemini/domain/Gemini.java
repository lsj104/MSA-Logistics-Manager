package com.team12.ai.gemini.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "p_gemini", schema = "s_ai")
public class Gemini {

    @Id
    @GeneratedValue
    @Column(name = "gemini_id", unique = true, nullable = false)
    private UUID id;

    @Column(name = "request_contents", columnDefinition = "TEXT")
    private String requestContents;

    @Column(name = "response_contents", columnDefinition = "TEXT")
    private String responseContents;


}
