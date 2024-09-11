package com.team12.company_product.company.service;

import com.team12.company_product.company.dto.CreateCompanyRequestDto;
import com.team12.company_product.company.dto.CreateCompanyResponseDto;
import com.team12.company_product.company.dto.DeleteCompanyResponseDto;
import com.team12.company_product.company.dto.GetCompanyResponseDto;
import com.team12.company_product.company.dto.UpdateCompanyRequestDto;
import com.team12.company_product.company.dto.UpdateCompanyResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CompanyService {

    // 업체 생성
    CreateCompanyResponseDto createCompany(CreateCompanyRequestDto requestDto);

    // 업체 상세 조회
    GetCompanyResponseDto getCompany(String companyId);

    // 모든 업체 조회
    Page<GetCompanyResponseDto> getAllCompany(Pageable pageable);

    // 업체 수정
    UpdateCompanyResponseDto updateCompany(UpdateCompanyRequestDto requestDto, String companyId);

    // 업체 삭제
    DeleteCompanyResponseDto deleteCompany(String companyId, Long userId);

    // 업체 검색
    Page<GetCompanyResponseDto> searchCompany(String keyword, Pageable pageable);
}
