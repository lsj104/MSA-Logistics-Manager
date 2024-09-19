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
    private Long userId;
    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;
    private UUID departmentId;
    private UUID arrivalHubId;
    private String address;
    private String receiver;
    private String receiverEmail;


    public void update(DeliveryReqDto deliveryReqDto) {
        this.orderId = deliveryReqDto.getOrderId();
        this.departmentId = deliveryReqDto.getDepartmentId();
        this.arrivalHubId = deliveryReqDto.getArrivalHubId();
        this.address = deliveryReqDto.getAddress();
        this.receiver = deliveryReqDto.getReceiver();
        this.receiverEmail = deliveryReqDto.getReceiverEmail();
        this.setUpdatedBy(0L);
    }

    public enum DeliveryStatus {
        // 준비중, 배송중, 배송완료
        PREPARING, DELIVERING, DELIVERED
    }

    // 주문 수정
    public void updateDeliveryInfo(String address, String receiver, String receiverEmail) {
        if (address != null) {
            this.address = address;
        }
        if (receiver != null) {
            this.receiver = receiver;
        }
        if (receiverEmail != null) {
            this.receiverEmail = receiverEmail;
        }
    }


}

