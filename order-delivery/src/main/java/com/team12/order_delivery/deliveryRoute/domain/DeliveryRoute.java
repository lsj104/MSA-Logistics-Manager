package com.team12.order_delivery.deliveryRoute.domain;

import com.team12.common.audit.AuditingEntity;
import com.team12.order_delivery.delivery.domain.Delivery;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "p_delivery_route", schema = "s_order_delivery")
public class DeliveryRoute extends AuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;
    private int sequence;
    private String startPoint;
    private String endPoint;
    private Double estimatedDistance;
    private Double estimatedTime;
    private Double actualDistance;
    private Double actualTime;
    private LocalDateTime startAt;
    private LocalDateTime endAt;

    @Enumerated(EnumType.STRING)
    private RouteStatus status;
    private Long deliveryPersonId;

    public enum RouteStatus {
        // 대기중, 배송중, 도착 완료
        WAITING, DELIVERING, ARRIVED
    }

}
