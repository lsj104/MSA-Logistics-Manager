package com.team12.order_delivery.delivery.domain;

import com.team12.common.audit.AuditingEntity;
import com.team12.order_delivery.delivery.dto.DeliveryReqDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "p_delivery", schema = "s_order_delivery")
@SQLDelete(sql = "UPDATE s_order_delivery.p_delivery SET is_deleted = true WHERE id = ?")
public class Delivery extends AuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private UUID orderId;
    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;
    private UUID fromHubId;
    private UUID toHubId;
    private String address;
    private String receiver;
    private String receiverEmail;


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

