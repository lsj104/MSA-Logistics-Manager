package com.team12.company_product.product.domain;

import com.team12.common.audit.AuditingEntity;
import com.team12.company_product.product.dto.CreateProductRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "p_product")
@Getter
@NoArgsConstructor
public class Product extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String productId;

    @Column(name = "company_id")
    private String companyId;

    @Column(name = "hub_id")
    private String hubId;

    @Column(name = "product_name")
    private String productName;


    @Builder
    public Product(String productId, String companyId, String hubId, String productName) {
        this.productId = productId;
        this.companyId = companyId;
        this.hubId = hubId;
        this.productName = productName;
    }

    public static Product of(CreateProductRequestDto requestDto) {
        return Product.builder()
                .companyId(requestDto.companyId())
                .hubId(requestDto.hubId())
                .productName(requestDto.productName())
                .build();
    }
}
