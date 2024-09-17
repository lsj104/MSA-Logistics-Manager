package com.team12.order_delivery.order.service;

import com.team12.order_delivery.order.dto.request.CreateOrderRequestDto;
import com.team12.order_delivery.order.dto.response.CreateOrderResponseDto;

public interface OrderService {

    // 주문 생성
    CreateOrderResponseDto createOrder(CreateOrderRequestDto requestDto);
}
