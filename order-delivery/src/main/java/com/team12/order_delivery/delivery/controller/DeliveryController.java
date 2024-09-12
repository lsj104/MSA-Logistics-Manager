package com.team12.order_delivery.delivery.controller;

import com.team12.common.exception.response.SuccessResponse;
import com.team12.order_delivery.delivery.dto.DeliveryReqDto;
import com.team12.order_delivery.delivery.message.SuccessMessage;
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

    // 배송 정보 수정, 삭제 API 추가
    @PutMapping("")
    public SuccessResponse<?> updateDelivery(@RequestParam String deliveryId ,@RequestBody DeliveryReqDto deliveryReqDto) {
        return SuccessResponse.success(SuccessMessage.UPDATE_DELIVERY.getHttpStatus().value(), SuccessMessage.UPDATE_DELIVERY.getMessage(), deliveryService.updateDelivery(deliveryId, deliveryReqDto));
    }

    @DeleteMapping("")
    public SuccessResponse<?> deleteDelivery(@RequestParam String deliveryId) {
        deliveryService.deleteDelivery(deliveryId);
        return SuccessResponse.success(SuccessMessage.DELETE_DELIVERY.getHttpStatus().value(), SuccessMessage.DELETE_DELIVERY.getMessage());
    }



    // 배송 상태 업데이트? -> 배송 경로 정보도 같이 업데이트 해야되는데 ...



}
