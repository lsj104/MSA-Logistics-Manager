package com.team12.order_delivery.order.service;

import com.team12.order_delivery.order.company.CompanyResponseDto;
import com.team12.order_delivery.order.company.CompanyService;
import com.team12.order_delivery.order.company.CompanyType;
import com.team12.order_delivery.order.domain.Order;
import com.team12.order_delivery.order.dto.request.CreateOrderRequestDto;
import com.team12.order_delivery.order.dto.response.CreateOrderResponseDto;
import com.team12.order_delivery.order.exception.ExceptionMessage;
import com.team12.order_delivery.order.exception.OrderException;
import com.team12.order_delivery.order.product.ProductResponseDto;
import com.team12.order_delivery.order.product.ProductService;
import com.team12.order_delivery.order.repository.OrderRepository;
import com.team12.order_delivery.delivery.domain.Delivery;
import com.team12.order_delivery.delivery.dto.DeliveryReqDto;
import com.team12.order_delivery.delivery.dto.DeliveryResDto;
import com.team12.order_delivery.delivery.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
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
        DeliveryReqDto deliveryReqDto = createDeliveryReqDto(order, requestDto);
        DeliveryResDto deliveryResDto = deliveryService.createDelivery(deliveryReqDto);
        Delivery delivery = deliveryService.findById(deliveryResDto.getDeliveryId());
        order.assignDelivery(delivery);
        orderRepository.save(order);
    }

    private DeliveryReqDto createDeliveryReqDto(Order order, CreateOrderRequestDto requestDto) {
        return DeliveryReqDto.builder()
                .orderId(order.getOrderId())
                .address(requestDto.address())
                .receiver(requestDto.receiver())
                .receiverEmail(requestDto.receiverEmail())
                .build();
    }


}

