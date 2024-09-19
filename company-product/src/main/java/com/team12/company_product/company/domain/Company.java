package com.team12.company_product.company.domain;

import com.team12.common.audit.AuditingEntity;
import com.team12.company_product.company.dto.request.CreateCompanyRequestDto;
import com.team12.company_product.company.dto.request.UpdateCompanyRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


// TODO: nullable = false 추가
@Entity(name = "p_company")
@Getter
@NoArgsConstructor
@Slf4j
public class Company extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String companyId;

    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Column(name = "company_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private CompanyType companyType;

    @Column(name = "hub_id", nullable = false)
    private UUID hubId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "address", nullable = false)
    private String address;

    @Builder
    public Company(String companyId, String companyName, CompanyType companyType, UUID hubId,
            Long userId, String address) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.companyType = companyType;
        this.hubId = hubId;
        this.userId = userId;
        this.address = address;
    }


    public static Company of(CreateCompanyRequestDto requestDto) {

        log.info(String.valueOf(requestDto.hubId()));
        return Company.builder()
                .companyName(requestDto.companyName())
                .companyType(requestDto.companyType())
                .hubId(requestDto.hubId())
                .address(requestDto.address())
                .build();
    }


    // 업체 수정
    public void update(UpdateCompanyRequestDto requestDto) {
        this.companyName =
                requestDto.companyName() != null ? requestDto.companyName() : this.companyName;
        this.companyType =
                requestDto.companyType() != null ? requestDto.companyType() : this.companyType;
        this.hubId = requestDto.hubId() != null ? requestDto.hubId() : this.hubId;
        this.address = requestDto.address() != null ? requestDto.address() : this.address;
    }

    // 업체 삭제
    public void delete(Long userId) {
        super.setIsDeleted(true);
        super.setDeletedAt(LocalDateTime.now());
        super.setDeletedBy(userId);
    }


}
