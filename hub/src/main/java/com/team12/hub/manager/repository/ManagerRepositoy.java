package com.team12.hub.manager.repository;

import com.team12.hub.hub.domain.Hub;
import com.team12.hub.manager.domain.Manager;
import com.team12.hub.manager.type.ManagerType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ManagerRepositoy extends JpaRepository<Manager, Long>, JpaSpecificationExecutor {
    Optional<Manager> findByIdAndIsDeleted(Long id, boolean isDeleted);

    @Query("select m from Manager m where m.type = :managerType ")
    List<Manager> findByType(ManagerType managerType);

    List<Manager> findByHubAndType(Hub hub, ManagerType managerType);
}
