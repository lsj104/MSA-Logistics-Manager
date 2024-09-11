package com.team12.hub.domain;


import com.team12.common.audit.AuditingEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "P_HUB")
public class Hub extends AuditingEntity {
    @Id
    @GeneratedValue
    @Column(name = "hub_id")
    private UUID id;  // 허브 ID(UUID)

    @Column(name = "hub_name", nullable = false)
    private String name;  // 허브 이름

    @Column(name = "address", nullable = false)
    private String address;  // 주소

    @Column(name = "latitude", nullable = false)
    private BigDecimal latitude;  // 위도

    @Column(name = "longitude", nullable = false)
    private BigDecimal longitude;  // 경도

    public Hub(UUID id, String name, String address, BigDecimal latitude, BigDecimal longitude, boolean isDeleted) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.setIsDeleted(isDeleted);
    }
}
