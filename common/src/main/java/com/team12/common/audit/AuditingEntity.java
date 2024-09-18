package com.team12.common.audit;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
@Setter
public abstract class AuditingEntity {
    @CreatedDate
    @Column(name="created_at", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @CreatedBy
    @Column(name="created_by")
    private Long createdBy;

    @LastModifiedDate
    @Column(name="updated_at",nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updatedAt;

    @LastModifiedBy
    @Column(name="updated_by", nullable = true)
    private Long updatedBy;

    @Column(name="deleted_at",nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime deletedAt;

    @Column(name="deleted_by",nullable = true)
    private Long deletedBy;

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;


}
