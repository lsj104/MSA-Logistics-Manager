package com.team12.company_product.product.domain;

import com.team12.common.audit.AuditingEntity;
import com.team12.company_product.company.domain.Company;
import com.team12.company_product.product.dto.request.CreateProductRequestDto;
import com.team12.company_product.product.dto.request.UpdateProductRequestDto;
import com.team12.company_product.product.exception.ExceptionMessage;
import com.team12.company_product.product.exception.ProductException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.UUID;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column(name = "hubId", nullable = false)
    private UUID hubId;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "quantity", nullable = false)
    private Long quantity;

    @Column(name = "user_id")
    private Long userId;

    @Builder
    public Product(String productId, Company company, UUID hubId, String productName, Long quantity,
            Long userId) {
        this.productId = productId;
        this.company = company;
        this.hubId = hubId;
        this.productName = productName;
        this.quantity = quantity;
        this.userId = userId;
    }


    public static Product of(CreateProductRequestDto requestDto, Company company, Long userId) {
        return Product.builder()
                .company(company)
                .hubId(requestDto.hubId())
                .productName(requestDto.productName())
                .quantity(requestDto.quantity())
                .userId(userId)
                .build();
    }

    public void update(UpdateProductRequestDto requestDto, Company company) {
        this.company = company != null ? company : this.company;
        this.productName =
                requestDto.productName() != null ? requestDto.productName() : this.productName;
        this.hubId = requestDto.hubId() != null ? requestDto.hubId() : this.hubId;
        this.quantity = requestDto.quantity() != null ? requestDto.quantity() : this.quantity;
    }

    public void delete(Long userId) {
        super.setIsDeleted(true);
        super.setDeletedAt(LocalDateTime.now());
        super.setDeletedBy(userId);
    }

    public void reduceQuantity(Long quantity) {
        if (this.quantity < quantity) {
            throw new ProductException(ExceptionMessage.INSUFFICIENT_PRODUCT_QUANTITY);
        }
        this.quantity -= quantity;
    }

    public void setQuantity(Long newQuantity) {
        this.quantity = newQuantity;
    }
}
