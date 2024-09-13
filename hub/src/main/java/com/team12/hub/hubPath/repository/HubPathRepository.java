package com.team12.hub.hubPath.repository;

import com.team12.hub.hubPath.domain.HubPath;
import org.antlr.v4.runtime.atn.SemanticContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface HubPathRepository extends JpaRepository<HubPath, UUID>, JpaSpecificationExecutor<HubPath> {

    Optional<HubPath> findByIdAndIsDeleted(UUID hubPathId, boolean isDeleted);

    @Query("SELECT p.id FROM HubPath p WHERE (p.fromHub.id = :hubId OR p.toHub.id = :hubId) and p.isDeleted = false")
    List<UUID> findHubPathsByHubId(@Param("hubId") UUID hubId);
}
