package com.team12.order_delivery.delivery.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "p_delivery_route", schema = "s_order_delivery")
public class DeliveryRoute {
    @Id
    private UUID id;
    @CollectionTable(name = "p_delivery", joinColumns = @JoinColumn(name = "id"))
    private UUID deliveryId;
    private int sequence;
    private UUID fromHubId;
    private UUID toHubId;
    private Double estimatedDistance;
    private Double estimatedTime;
    private Double actualDistance;
    private Double actualTime;
    @Enumerated(EnumType.STRING)
    private RouteStatus status;
//    private int deliveryPersonId;

    public enum RouteStatus {
        // 대기중, 배송중, 도착 완료
        WAITING, DELIVERING, ARRIVED
    }

}
