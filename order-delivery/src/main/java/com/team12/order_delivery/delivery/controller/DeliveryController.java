package com.team12.order_delivery.delivery.controller;

import com.team12.common.exception.response.SuccessResponse;
import com.team12.order_delivery.delivery.dto.DeliveryReqDto;
import com.team12.order_delivery.util.SuccessMessage;
import com.team12.order_delivery.delivery.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/delivery")
@RestController
public class DeliveryController {
    private final DeliveryService deliveryService;

    @PostMapping("")
    public SuccessResponse<?> createDelivery(@RequestBody DeliveryReqDto deliveryReqDto) {
        return SuccessResponse.success(SuccessMessage.CREATE_DELIVERY.getHttpStatus().value(), SuccessMessage.CREATE_DELIVERY.getMessage(), deliveryService.createDelivery(deliveryReqDto));
    }


    @GetMapping("")
    public SuccessResponse<?> getDelivery(@RequestParam String deliveryId) {
        return SuccessResponse.success(SuccessMessage.GET_DELIVERY.getHttpStatus().value(), SuccessMessage.GET_DELIVERY.getMessage(), deliveryService.getDelivery(deliveryId));
    }

    @GetMapping("/all")
    public SuccessResponse<?> getAllDelivery(Pageable pageable) {
        return SuccessResponse.success(SuccessMessage.GET_DELIVERY.getHttpStatus().value(), SuccessMessage.GET_DELIVERY.getMessage(), deliveryService.getAllDelivery(pageable));
    }

    @PutMapping("")
    public SuccessResponse<?> updateDelivery(@RequestParam String deliveryId, @RequestBody DeliveryReqDto deliveryReqDto) {
        return SuccessResponse.success(SuccessMessage.UPDATE_DELIVERY.getHttpStatus().value(), SuccessMessage.UPDATE_DELIVERY.getMessage(), deliveryService.updateDelivery(deliveryId, deliveryReqDto));
    }

    @PatchMapping("")
    public SuccessResponse<?> updateDeliveryStatus(@RequestParam String deliveryId, @RequestParam String deliveryStatus) {
        return SuccessResponse.success(SuccessMessage.UPDATE_DELIVERY.getHttpStatus().value(), SuccessMessage.UPDATE_DELIVERY.getMessage(), deliveryService.updateDeliveryStatus(deliveryId, deliveryStatus));
    }

    @DeleteMapping("")
    public SuccessResponse<?> deleteDelivery(@RequestParam String deliveryId) {
        deliveryService.deleteDelivery(deliveryId);
        return SuccessResponse.success(SuccessMessage.DELETE_DELIVERY.getHttpStatus().value(), SuccessMessage.DELETE_DELIVERY.getMessage());
    }

    @PatchMapping("/route")
    public SuccessResponse<?> updateDeliveryRouteStatus(@RequestParam String deliveryRouteId, @RequestParam String deliveryRouteStatus) {
        return SuccessResponse.success(SuccessMessage.UPDATE_DELIVERY_ROUTE.getHttpStatus().value(), SuccessMessage.UPDATE_DELIVERY_ROUTE.getMessage(), deliveryService.updateDeliveryRouteStatus(deliveryRouteId, deliveryRouteStatus));
    }
    // Delivery, Delivery Route 생성됨
    // sequence1인 Delivery Route에서 배송 시작 -> Delivery의 status 변경
    // 마지막 sequence인 Delivery Route에서 배송 완료 -> Delivery의 status 변경

}
