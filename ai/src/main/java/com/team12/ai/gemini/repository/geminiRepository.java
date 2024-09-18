package com.team12.ai.gemini.repository;

import com.team12.ai.gemini.domain.Gemini;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface geminiRepository extends JpaRepository<Gemini, UUID> {
}
