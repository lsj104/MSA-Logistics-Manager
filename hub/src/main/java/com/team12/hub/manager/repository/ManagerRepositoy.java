package com.team12.hub.manager.repository;

import com.team12.hub.manager.domain.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.Optional;

public interface ManagerRepositoy extends JpaRepository<Manager, Long> {
    Optional<Manager> findByIdAndIsDeleted(Long id, boolean isDeleted);
}
