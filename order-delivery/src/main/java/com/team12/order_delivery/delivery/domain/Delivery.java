package com.team12.order_delivery.delivery.domain;

import com.team12.order_delivery.delivery.dto.DeliveryReqDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "p_delivery", schema = "s_order_delivery")
//@SQLDelete(sql = "UPDATE p_delivery SET is_deleted = true WHERE id = ?")
public class Delivery {
    @Id
    @GeneratedValue
    private UUID id;
    private UUID orderId;
    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;
    private UUID fromHubId;
    private UUID toHubId;
    private String address;
    private String receiver;
    private String receiverEmail;

    public Delivery(DeliveryReqDto deliveryReqDto) {
        this.id = UUID.randomUUID();
        this.orderId = deliveryReqDto.getOrderId();
        this.fromHubId = deliveryReqDto.getFromHubId();
        this.toHubId = deliveryReqDto.getToHubId();
        this.address = deliveryReqDto.getAddress();
        this.receiver = deliveryReqDto.getReceiver();
        this.receiverEmail = deliveryReqDto.getReceiverEmail();
        this.deliveryStatus = DeliveryStatus.PREPARING;
    }

    public void update(DeliveryReqDto deliveryReqDto) {
        this.orderId = deliveryReqDto.getOrderId();
        this.fromHubId = deliveryReqDto.getFromHubId();
        this.toHubId = deliveryReqDto.getToHubId();
        this.address = deliveryReqDto.getAddress();
        this.receiver = deliveryReqDto.getReceiver();
        this.receiverEmail = deliveryReqDto.getReceiverEmail();
    }

    public enum DeliveryStatus {
        // 준비중, 배송중, 배송완료
        PREPARING, DELIVERING, DELIVERED
    }
}

