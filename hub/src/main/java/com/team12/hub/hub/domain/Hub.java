package com.team12.hub.hub.domain;

import com.team12.common.audit.AuditingEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "p_hub", schema = "s_hub")
public class Hub extends AuditingEntity {
    @Id
    @GeneratedValue
    @Column(name = "hub_id")
    private UUID id;  // 허브 ID(UUID)

    @Column(name = "hub_name", nullable = false, unique = true)
    private String name;  // 허브 이름

    @Column(name = "address", nullable = false)
    private String address;  // 주소

    @Column(name = "latitude", nullable = false)
    private String latitude;  // 위도

    @Column(name = "longitude", nullable = false)
    private String longitude;  // 경도

    public Hub(UUID id, String name, String address, String latitude, String longitude, boolean isDeleted) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.setIsDeleted(isDeleted);
    }
}
