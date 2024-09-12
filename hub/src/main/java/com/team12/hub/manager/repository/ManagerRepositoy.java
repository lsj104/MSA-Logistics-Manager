package com.team12.hub.manager.repository;

import com.team12.hub.manager.domain.Manager;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.awt.print.Pageable;
import java.math.BigInteger;
import java.util.Optional;

public interface ManagerRepositoy extends JpaRepository<Manager, Long>, JpaSpecificationExecutor {
    Optional<Manager> findByIdAndIsDeleted(Long id, boolean isDeleted);
}
