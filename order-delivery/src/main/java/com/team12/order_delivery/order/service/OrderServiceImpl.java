package com.team12.order_delivery.order.service;

import com.team12.order_delivery.order.company.CompanyResponseDto;
import com.team12.order_delivery.order.company.CompanyService;
import com.team12.order_delivery.order.company.CompanyType;
import com.team12.order_delivery.order.domain.Order;
import com.team12.order_delivery.order.dto.request.CreateOrderRequestDto;
import com.team12.order_delivery.order.dto.request.UpdateOrderRequestDto;
import com.team12.order_delivery.order.dto.response.CreateOrderResponseDto;
import com.team12.order_delivery.order.dto.response.DeleteOrderResponseDto;
import com.team12.order_delivery.order.dto.response.GetOrderResponseDto;
import com.team12.order_delivery.order.dto.response.UpdateOrderResponseDto;
import com.team12.order_delivery.order.exception.ExceptionMessage;
import com.team12.order_delivery.order.exception.OrderException;
import com.team12.order_delivery.order.product.ProductResponseDto;
import com.team12.order_delivery.order.product.ProductService;
import com.team12.order_delivery.order.repository.OrderRepository;
import com.team12.order_delivery.delivery.domain.Delivery;
import com.team12.order_delivery.delivery.dto.DeliveryReqDto;
import com.team12.order_delivery.delivery.dto.DeliveryResDto;
import com.team12.order_delivery.delivery.service.DeliveryService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final DeliveryService deliveryService;
    private final CompanyService companyService;
    private final ProductService productService;

    // 주문 생성
    @Transactional
    public CreateOrderResponseDto createOrder(CreateOrderRequestDto requestDto) {
        validateCompanies(requestDto);
        validateProduct(requestDto.productId());

        Order order = createAndSaveOrder(requestDto);
        createAndAssignDelivery(order, requestDto);

        return CreateOrderResponseDto.from(order);
    }

    // 주문 상세조회
    @Transactional(readOnly = true)
    public GetOrderResponseDto getOrder(UUID orderId) {
        Order order = findById(orderId);
        return GetOrderResponseDto.from(order);
    }

    // 주문 목록 조회
    @Transactional(readOnly = true)
    public Page<GetOrderResponseDto> getAllOrder(Pageable pageable) {
        Page<Order> orders = orderRepository.findAll(pageable);
        return orders.map(GetOrderResponseDto::from);
    }

    // 주문 수정
    @Transactional
    public UpdateOrderResponseDto updateOrder(UpdateOrderRequestDto requestDto, UUID orderId) {
        Order order = findById(orderId);
        order.update(requestDto);

        return UpdateOrderResponseDto.from(order, order.getDelivery());
    }

    // 주문 삭제
    @Transactional
    public DeleteOrderResponseDto deleteOrder(UUID orderId, Long userId) {
        Order order = findById(orderId);
        order.delete(userId);

        return DeleteOrderResponseDto.from(order);
    }


    private void validateCompanies(CreateOrderRequestDto requestDto) {
        validateCompany(requestDto.producerCompany(), CompanyType.PRODUCER);
        validateCompany(requestDto.receiverCompany(), CompanyType.RECEIVER);
    }

    private void validateCompany(String companyId, CompanyType expectedType) {
        CompanyResponseDto company = companyService.getCompany(companyId);

        if (company == null) {
            throw new OrderException(ExceptionMessage.COMPANY_NOT_FOUND);
        }
        if (!expectedType.equals(company.companyType())) {
            throw new OrderException(ExceptionMessage.COMPANY_TYPE_NOT_MATCH);
        }
    }

    private void validateProduct(String productId) {
        ProductResponseDto product = productService.getProduct(productId);
        if (product == null) {
            throw new OrderException(ExceptionMessage.PRODUCT_NOT_FOUND);
        }
    }

    private Order createAndSaveOrder(CreateOrderRequestDto requestDto) {
        Order order = Order.createOrder(
                requestDto.producerCompany(),
                requestDto.receiverCompany(),
                requestDto.quantity(),
                requestDto.productId()
        );
        return orderRepository.save(order);
    }

    private void createAndAssignDelivery(Order order, CreateOrderRequestDto requestDto) {
        CompanyResponseDto producerCompany = companyService.getCompany(
                requestDto.producerCompany());
        CompanyResponseDto receiverCompany = companyService.getCompany(
                requestDto.receiverCompany());

        DeliveryReqDto deliveryReqDto = DeliveryReqDto.builder()
                .orderId(order.getOrderId())
                .departmentId(UUID.fromString(producerCompany.hubId()))
                .arrivalHubId(UUID.fromString(receiverCompany.hubId()))
                .address(requestDto.address())
                .receiver(requestDto.receiver())
                .receiverEmail(requestDto.receiverEmail())
                .build();

        log.info(deliveryReqDto.toString());

        DeliveryResDto deliveryResDto = deliveryService.createDelivery(deliveryReqDto);
        Delivery delivery = deliveryService.findById(deliveryResDto.getDeliveryId());
        order.assignDelivery(delivery);
        orderRepository.save(order);
    }

    // ID로 주문 검색
    @Transactional
    public Order findById(UUID orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderException(ExceptionMessage.ORDER_NOT_FOUND));
    }


}

