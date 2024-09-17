package com.team12.order_delivery.order.domain;

import com.team12.common.audit.AuditingEntity;
import com.team12.order_delivery.delivery.domain.Delivery;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity(name = "p_order")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID orderId;

    @Column(name = "producer_company_id", nullable = false)
    private String producerCompanyId;

    @Column(name = "receiver_company_id", nullable = false)
    private String receiverCompanyId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @Column(name = "quantity", nullable = false)
    private Long quantity;

    @Column(name = "product_id", nullable = false)
    private String productId;

    private Order(String producerCompanyId, String receiverCompanyId, Long quantity, String productId) {
        this.producerCompanyId = producerCompanyId;
        this.receiverCompanyId = receiverCompanyId;
        this.quantity = quantity;
        this.productId = productId;
    }

    public static Order createOrder(String producerCompanyId, String receiverCompanyId, Long quantity, String productId) {
        return new Order(producerCompanyId, receiverCompanyId, quantity, productId);
    }

    public void assignDelivery(Delivery delivery) {
        this.delivery = delivery;
    }
}