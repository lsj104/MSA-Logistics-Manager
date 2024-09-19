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

//    @OneToOne(fetch = FetchType.LAZY)
//    @MapsId("managerId")  // manager_id를 DeliveryPerson의 ID로 사용하고, User의 user_id와 매핑
//    @JoinColumn(name = "user_id")  // USER 엔티티의 user_id를 외래 키로 참조
//    private User user;  // USER 엔티티와 일대일 연관관계

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "hub_id", referencedColumnName = "hub_id")
    private Hub hub;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ManagerType type;  // 허브 간 이동 담당자 또는 업체 배송 담당자

}
