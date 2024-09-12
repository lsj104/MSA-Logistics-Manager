package com.team12.hub.hub.repository;

import com.team12.hub.hub.domain.Hub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface HubRepository extends JpaRepository<Hub, UUID> {
    Optional<Hub> findByIdAndIsDeleted(UUID hubId, boolean b);
}
