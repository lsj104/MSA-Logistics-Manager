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
    private String startPoint;
    private String endPoint;
    private Double estimatedDistance;
    private Double estimatedTime;
    private Double actualDistance;
    private Double actualTime;
    private String status;
    private Long deliveryPersonId;

    public RouteResDto(DeliveryRoute deliveryRoute) {
        this.id = deliveryRoute.getId();
        this.sequence = deliveryRoute.getSequence();
        this.startPoint = deliveryRoute.getStartPoint();
        this.endPoint = deliveryRoute.getEndPoint();
        this.estimatedDistance = deliveryRoute.getEstimatedDistance();
        this.estimatedTime = deliveryRoute.getEstimatedTime();
        this.actualDistance = deliveryRoute.getActualDistance();
        this.actualTime = deliveryRoute.getActualTime();
        this.status = deliveryRoute.getStatus().name();
        this.deliveryPersonId = deliveryRoute.getDeliveryPersonId();
    }
}
