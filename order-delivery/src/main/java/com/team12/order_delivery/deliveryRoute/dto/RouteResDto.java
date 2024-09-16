package com.team12.order_delivery.deliveryRoute.dto;

import com.team12.order_delivery.deliveryRoute.domain.DeliveryRoute;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class RouteResDto {
    private UUID id;
    private int sequence;
    private UUID fromHubId;
    private UUID toHubId;
    private Double estimatedDistance;
    private Double estimatedTime;
    private Double actualDistance;
    private Double actualTime;
    private String status;

    public RouteResDto(DeliveryRoute deliveryRoute) {
        this.id = deliveryRoute.getId();
        this.sequence = deliveryRoute.getSequence();
        this.fromHubId = deliveryRoute.getFromHubId();
        this.toHubId = deliveryRoute.getToHubId();
        this.estimatedDistance = deliveryRoute.getEstimatedDistance();
        this.estimatedTime = deliveryRoute.getEstimatedTime();
        this.actualDistance = deliveryRoute.getActualDistance();
        this.actualTime = deliveryRoute.getActualTime();
        this.status = deliveryRoute.getStatus().name();
    }
}
