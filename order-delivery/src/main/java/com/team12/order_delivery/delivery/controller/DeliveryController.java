package com.team12.order_delivery.delivery.controller;

import com.team12.order_delivery.delivery.dto.DeliveryReqDto;
import com.team12.order_delivery.delivery.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/delivery")
@RestController
public class DeliveryController {
    private final DeliveryService deliveryService;

    @PostMapping("")
    public ResponseEntity<?> createDelivery(@RequestBody DeliveryReqDto deliveryReqDto) {
        return ResponseEntity.ok(deliveryService.createDelivery(deliveryReqDto));
    }


}
