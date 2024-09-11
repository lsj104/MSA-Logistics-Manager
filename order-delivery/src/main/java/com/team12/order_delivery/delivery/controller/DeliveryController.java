package com.team12.order_delivery.delivery.controller;

import com.team12.order_delivery.delivery.dto.DeliveryReqDto;
import com.team12.order_delivery.delivery.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("")
    public ResponseEntity<?> getDelivery(@RequestParam String deliveryId) {
        return ResponseEntity.ok(deliveryService.getDelivery(deliveryId));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllDelivery(Pageable pageable) {
        return ResponseEntity.ok(deliveryService.getAllDelivery(pageable));
    }

    // 배송 정보 수정, 삭제 API 추가
    @PutMapping("")
    public ResponseEntity<?> updateDelivery(@RequestParam String deliveryId ,@RequestBody DeliveryReqDto deliveryReqDto) {
        return ResponseEntity.ok(deliveryService.updateDelivery(deliveryId,deliveryReqDto));
    }

    @DeleteMapping("")
    public ResponseEntity<?> deleteDelivery(@RequestParam String deliveryId) {
        return ResponseEntity.ok(deliveryService.deleteDelivery(deliveryId));
    }



    // 배송 상태 업데이트? -> 배송 경로 정보도 같이 업데이트 해야되는데 ...



}
