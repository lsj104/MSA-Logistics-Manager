package com.team12.hub.manager.domain;

import com.team12.common.audit.AuditingEntity;
import com.team12.hub.hub.domain.Hub;
import com.team12.hub.manager.type.ManagerType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Manager extends AuditingEntity {

    @Id
    @Column(name = "manager_id")  // DeliveryPerson의 ID로 사용
    private Long id;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "hub_id", referencedColumnName = "hub_id", nullable = false)
    private Hub hub;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ManagerType type;  // 허브 간 이동 담당자 또는 업체 배송 담당자

}
