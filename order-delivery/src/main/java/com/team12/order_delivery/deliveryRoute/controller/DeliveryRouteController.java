package com.team12.order_delivery.deliveryRoute.controller;

import com.team12.common.exception.response.SuccessResponse;
import com.team12.order_delivery.deliveryRoute.service.DeliveryRouteService;
import com.team12.order_delivery.util.SuccessMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/route")
public class DeliveryRouteController {
    private final DeliveryRouteService deliveryRouteService;
    @GetMapping("")
    public SuccessResponse<?> getAllDeliveryRoute(@RequestParam String deliveryId) {
        return SuccessResponse.success(SuccessMessage.GET_DELIVERY_ROUTE.getHttpStatus().value(), SuccessMessage.GET_DELIVERY_ROUTE.getMessage(), deliveryRouteService.getAllDeliveryRoutes(deliveryId));
    }

    @GetMapping("/detail")
    public SuccessResponse<?> getDeliveryRoute(@RequestParam String deliveryRouteId) {
        return SuccessResponse.success(SuccessMessage.GET_DELIVERY_ROUTE.getHttpStatus().value(), SuccessMessage.GET_DELIVERY_ROUTE.getMessage(), deliveryRouteService.getDeliveryRoute(deliveryRouteId));
    }


    @PutMapping("")
    public SuccessResponse<?> updateDeliveryRoute(@RequestParam String deliveryRouteId, @RequestParam String deliveryId, @RequestParam String sequence, @RequestParam String hubId) {
        return SuccessResponse.success(SuccessMessage.UPDATE_DELIVERY_ROUTE.getHttpStatus().value(), SuccessMessage.UPDATE_DELIVERY_ROUTE.getMessage(), deliveryRouteService.updateDeliveryRoute(deliveryRouteId, deliveryId, sequence, hubId));
    }

    @DeleteMapping("")
    public SuccessResponse<?> deleteDeliveryRoute(@RequestParam String deliveryRouteId) {
        deliveryRouteService.deleteDeliveryRoute(deliveryRouteId);
        return SuccessResponse.success(SuccessMessage.DELETE_DELIVERY_ROUTE.getHttpStatus().value(), SuccessMessage.DELETE_DELIVERY_ROUTE.getMessage());
    }


}
