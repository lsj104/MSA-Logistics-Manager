package com.team12.order_delivery.order.service;

import com.team12.order_delivery.delivery.domain.Delivery;
import com.team12.order_delivery.order.dto.request.CreateOrderRequestDto;
import com.team12.order_delivery.order.dto.request.UpdateOrderRequestDto;
import com.team12.order_delivery.order.dto.response.CreateOrderResponseDto;
import com.team12.order_delivery.order.dto.response.DeleteOrderResponseDto;
import com.team12.order_delivery.order.dto.response.GetOrderResponseDto;
import com.team12.order_delivery.order.dto.response.UpdateOrderResponseDto;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    // 주문 생성
    CreateOrderResponseDto createOrder(CreateOrderRequestDto requestDto);

    // 주문 상세조회
    GetOrderResponseDto getOrder(UUID orderId);

    // 모든 주문 조회
    Page<GetOrderResponseDto> getAllOrder(Pageable pageable);

    // 주문 수정
    UpdateOrderResponseDto updateOrder(UpdateOrderRequestDto requestDto, UUID orderId);

    // 주문 삭제
    DeleteOrderResponseDto deleteOrder(UUID orderId, Long userId);
}
