package com.team12.hub.hubPath.repository;

import com.team12.hub.hubPath.domain.HubPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface HubPathRepository extends JpaRepository<HubPath, UUID> {

    Optional<HubPath> findByIdAndIsDeleted(UUID hubPathId, boolean isDeleted);

    @Query("SELECT p.id FROM HubPath p WHERE p.fromHub = :hub OR p.toHub = :hub")
    List<UUID> findHubPathsByHubId(@Param("hub") UUID hub);
}
