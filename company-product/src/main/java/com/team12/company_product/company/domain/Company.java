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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


// TODO: nullable = false 추가
@Entity(name = "p_company")
@Getter
@NoArgsConstructor
public class Company extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String companyId;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "company_type")
    @Enumerated(EnumType.STRING)
    private CompanyType companyType;

    @Column(name = "hubId")
    private String hubId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "address")
    private String address;

    @Builder
    public Company(String companyId, String companyName, CompanyType companyType, String hubId,
            Long userId, String address) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.companyType = companyType;
        this.hubId = hubId;
        this.userId = userId;
        this.address = address;
    }

    public static Company of(CreateCompanyRequestDto requestDto) {
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
                requestDto.companyType() != null ? CompanyType.valueOf(requestDto.companyType())
                        : this.companyType;
        this.hubId = requestDto.hubId() != null ? requestDto.hubId() : this.hubId;
        this.address = requestDto.address() != null ? requestDto.address() : this.address;
    }

    // TODO: user추가
    // 업체 삭제
    public void delete(Long userId) {
        super.setIsDeleted(true);
        super.setDeletedAt(LocalDateTime.now());
        super.setDeletedBy(userId);
    }


}
