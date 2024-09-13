package com.team12.company_product.company.service;

import com.team12.company_product.company.domain.Company;
import com.team12.company_product.company.dto.request.CreateCompanyRequestDto;
import com.team12.company_product.company.dto.response.CreateCompanyResponseDto;
import com.team12.company_product.company.dto.response.DeleteCompanyResponseDto;
import com.team12.company_product.company.dto.response.GetCompanyResponseDto;
import com.team12.company_product.company.dto.request.UpdateCompanyRequestDto;
import com.team12.company_product.company.dto.response.UpdateCompanyResponseDto;
import com.team12.company_product.company.exception.CompanyException;
import com.team12.company_product.company.exception.ExceptionMessage;
import com.team12.company_product.company.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    // 업체 생성
    @Transactional
    public CreateCompanyResponseDto createCompany(CreateCompanyRequestDto requestDto) {

        Company company = Company.of(requestDto);
        companyRepository.save(company);
        return CreateCompanyResponseDto.from(company);
    }

    // 업체 상세 조회
    @Transactional(readOnly = true)
    public GetCompanyResponseDto getCompany(String companyId) {

        Company company = findById(companyId);
        return GetCompanyResponseDto.from(company);
    }

    // 모든 업체 조회
    @Transactional(readOnly = true)
    public Page<GetCompanyResponseDto> getAllCompany(Pageable pageable) {
        Page<Company> companies = companyRepository.findAll(pageable);
        return companies.map(GetCompanyResponseDto::from);
    }

    // 업체 수정
    @Transactional
    public UpdateCompanyResponseDto updateCompany(UpdateCompanyRequestDto requestDto,
            String companyId) {

        Company company = findById(companyId);
        company.update(requestDto);
        return UpdateCompanyResponseDto.from(company);
    }

    // 업체 삭제
    @Transactional
    public DeleteCompanyResponseDto deleteCompany(String companyId, Long userId) {

        Company company = findById(companyId);
        company.delete(userId);

        return DeleteCompanyResponseDto.from(company);

    }

    // 업체 검색
    @Transactional(readOnly = true)
    public Page<GetCompanyResponseDto> searchCompany(String keyword, Pageable pageable) {

        // 키워드가 없는 경우
        if (keyword == null || keyword.isEmpty()) {
            throw new CompanyException(ExceptionMessage.KEYWORD_EMPTY);
        }

        Page<Company> companyPage = Page.empty();

        if (keyword.startsWith("name:")) {
            // 이름 검색
            String nameKeyword = keyword.replaceFirst("name:", "").trim();
            companyPage = companyRepository.findByCompanyNameContaining(nameKeyword, pageable);
        } else if (keyword.startsWith("id:")) {
            // ID 검색
            String idKeyword = keyword.replaceFirst("id:", "").trim();
            companyPage = companyRepository.findByCompanyIdContaining(idKeyword, pageable);
        }

        if (companyPage.isEmpty()) {
            throw new CompanyException(ExceptionMessage.KEYWORD_NOT_PROVIDED);
        }

        return companyPage.map(GetCompanyResponseDto::from);
    }

    // ID로 업체 검색
    @Transactional
    public Company findById(String companyId) {
        return companyRepository.findById(companyId)
                .orElseThrow(() -> new CompanyException(ExceptionMessage.COMPANY_NOT_FOUND));
    }
}
