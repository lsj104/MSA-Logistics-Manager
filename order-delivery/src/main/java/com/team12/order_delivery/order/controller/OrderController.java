package com.team12.order_delivery.order.controller;


import static com.team12.common.exception.response.SuccessResponse.success;
import static com.team12.order_delivery.order.message.SuccessMessage.CREATE_ORDER;

import com.team12.common.exception.response.SuccessResponse;
import com.team12.order_delivery.order.dto.request.CreateOrderRequestDto;
import com.team12.order_delivery.order.dto.response.CreateOrderResponseDto;
import com.team12.order_delivery.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "주문", description = "주문 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    // TODO: 주문할때마다 수량 줄어들게 설정하기
    @Operation(summary = "주문 생성")
    @PostMapping
    public ResponseEntity<SuccessResponse<CreateOrderResponseDto>> createOrder(
            @RequestBody CreateOrderRequestDto requestDto) {

        return ResponseEntity.status(CREATE_ORDER.getHttpStatus())
                .body(success(CREATE_ORDER.getHttpStatus().value(),
                        CREATE_ORDER.getMessage(),
                        orderService.createOrder(requestDto)));

    }

}
