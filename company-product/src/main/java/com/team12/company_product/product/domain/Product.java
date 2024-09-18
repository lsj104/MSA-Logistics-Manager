package com.team12.company_product.product.domain;

import com.team12.common.audit.AuditingEntity;
import com.team12.company_product.company.domain.Company;
import com.team12.company_product.product.dto.request.CreateProductRequestDto;
import com.team12.company_product.product.dto.request.UpdateProductRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "p_product")
@Getter
@NoArgsConstructor
public class Product extends AuditingEntity {

// TODO: nullable = false 추가

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @Column(name = "hubId")
    private String hubId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "quantity")
    private Long quantity;


    @Builder
    public Product(String productId, Company company, String hubId, String productName,
            Long quantity) {
        this.productId = productId;
        this.company = company;
        this.hubId = hubId;
        this.productName = productName;
        this.quantity = quantity;
    }

    public static Product of(CreateProductRequestDto requestDto, Company company) {
        return Product.builder()
                .company(company)
                .hubId(requestDto.hubId())
                .productName(requestDto.productName())
                .quantity(requestDto.quantity())
                .build();
    }

    public void update(UpdateProductRequestDto requestDto, Company company) {
        this.company = company != null ? company : this.company;
        this.productName =
                requestDto.productName() != null ? requestDto.productName() : this.productName;
        this.hubId = requestDto.hub_id() != null ? requestDto.hub_id() : this.hubId;
    }

    public void delete(Long userId) {
        super.setIsDeleted(true);
        super.setDeletedAt(LocalDateTime.now());
        super.setDeletedBy(userId);
    }
}
