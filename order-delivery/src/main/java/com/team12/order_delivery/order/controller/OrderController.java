package com.team12.order_delivery.order.controller;


import static com.team12.common.exception.response.SuccessResponse.success;
import static com.team12.order_delivery.order.message.SuccessMessage.CREATE_ORDER;
import static com.team12.order_delivery.order.message.SuccessMessage.DELETE_ORDER;
import static com.team12.order_delivery.order.message.SuccessMessage.GET_ORDER;
import static com.team12.order_delivery.order.message.SuccessMessage.UPDATE_ORDER;

import com.team12.common.customPage.CustomPageResponse;
import com.team12.common.exception.response.SuccessResponse;
import com.team12.order_delivery.order.dto.request.CreateOrderRequestDto;
import com.team12.order_delivery.order.dto.request.UpdateOrderRequestDto;
import com.team12.order_delivery.order.dto.response.CreateOrderResponseDto;
import com.team12.order_delivery.order.dto.response.DeleteOrderResponseDto;
import com.team12.order_delivery.order.dto.response.GetOrderResponseDto;
import com.team12.order_delivery.order.dto.response.UpdateOrderResponseDto;
import com.team12.order_delivery.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "주문", description = "주문 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "주문 생성")
    @PostMapping
    public ResponseEntity<SuccessResponse<CreateOrderResponseDto>> createOrder(
            @RequestBody CreateOrderRequestDto requestDto) {

        return ResponseEntity.status(CREATE_ORDER.getHttpStatus())
                .body(success(CREATE_ORDER.getHttpStatus().value(),
                        CREATE_ORDER.getMessage(),
                        orderService.createOrder(requestDto)));

    }

    @Operation(summary = "주문 상세 조회")
    @GetMapping("{orderId}")
    public ResponseEntity<SuccessResponse<GetOrderResponseDto>> getOrder(
            @PathVariable("orderId") UUID orderId) {
        GetOrderResponseDto responseDto = orderService.getOrder(orderId);

        return ResponseEntity.status(GET_ORDER.getHttpStatus())
                .body(success(GET_ORDER.getHttpStatus().value(),
                        GET_ORDER.getMessage(), responseDto));
    }


    @Operation(summary = "모든 주문 조회")
    @GetMapping
    public ResponseEntity<SuccessResponse<CustomPageResponse<GetOrderResponseDto>>> getAllOrder(
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<GetOrderResponseDto> responseDto = orderService.getAllOrder(pageable);

        CustomPageResponse<GetOrderResponseDto> customPageResponse = new CustomPageResponse<>(
                responseDto);

        return ResponseEntity.status(GET_ORDER.getHttpStatus())
                .body(success(GET_ORDER.getHttpStatus().value(),
                        GET_ORDER.getMessage(), customPageResponse));
    }

    @Operation(summary = "주문 수정")
    @PutMapping("/{orderId}")
    public ResponseEntity<SuccessResponse<UpdateOrderResponseDto>> updateOrder(
            @PathVariable("orderId") UUID orderId,
            @RequestBody UpdateOrderRequestDto requestDto) {

        UpdateOrderResponseDto responseDto = orderService.updateOrder(requestDto, orderId);

        return ResponseEntity.status(UPDATE_ORDER.getHttpStatus())
                .body(success(UPDATE_ORDER.getHttpStatus().value(),
                        UPDATE_ORDER.getMessage(), responseDto));
    }

    @Operation(summary = "주문 삭제")
    @DeleteMapping("/{orderId}")
    public ResponseEntity<SuccessResponse<DeleteOrderResponseDto>> deleteOrder(
            @PathVariable("orderId") UUID orderId) {

        Long userId = null;
        DeleteOrderResponseDto responseDto = orderService.deleteOrder(orderId, userId);

        return ResponseEntity.status(DELETE_ORDER.getHttpStatus())
                .body(success(DELETE_ORDER.getHttpStatus().value(),
                        DELETE_ORDER.getMessage(), responseDto));

    }
}
