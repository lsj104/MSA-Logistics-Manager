package com.team12.order_delivery.deliveryRoute.controller;

import com.team12.common.exception.response.SuccessResponse;
import com.team12.order_delivery.deliveryRoute.service.DeliveryRouteService;
import com.team12.order_delivery.util.SuccessMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DeliveryRouteController {
    private final DeliveryRouteService deliveryRouteService;
    @GetMapping("/{deliveryId}")
    public SuccessResponse<?> getAllDeliveryRoute(@PathVariable String deliveryId) {
        return SuccessResponse.success(SuccessMessage.GET_DELIVERY_ROUTE.getHttpStatus().value(), SuccessMessage.GET_DELIVERY_ROUTE.getMessage(), deliveryRouteService.getAllDeliveryRoutes(deliveryId));
    }

    @GetMapping("/{deliveryRouteId}")
    public SuccessResponse<?> getDeliveryRoute(@PathVariable String deliveryRouteId) {
        return SuccessResponse.success(SuccessMessage.GET_DELIVERY_ROUTE.getHttpStatus().value(), SuccessMessage.GET_DELIVERY_ROUTE.getMessage(), deliveryRouteService.getDeliveryRoute(deliveryRouteId));
    }


}
