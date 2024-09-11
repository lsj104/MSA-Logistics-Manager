package com.team12.company_product.company.controller;

import static com.team12.company_product.company.message.SuccessMessage.CREATE_COMPANY;
import static com.team12.company_product.company.message.SuccessMessage.DELETE_COMPANY;
import static com.team12.company_product.company.message.SuccessMessage.GET_COMPANY;
import static com.team12.company_product.company.message.SuccessMessage.SEARCH_COMPANY;
import static com.team12.company_product.company.message.SuccessMessage.UPDATE_COMPANY;
import static com.team12.company_product.global.response.SuccessResponse.success;

import com.team12.company_product.company.dto.CreateCompanyRequestDto;
import com.team12.company_product.company.dto.DeleteCompanyResponseDto;
import com.team12.company_product.company.dto.GetCompanyResponseDto;
import com.team12.company_product.company.dto.UpdateCompanyRequestDto;
import com.team12.company_product.company.dto.UpdateCompanyResponseDto;
import com.team12.company_product.company.service.CompanyService;
import com.team12.company_product.global.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/companies")
public class CompanyController {

    private final CompanyService companyService;

    // TODO: user, hub 작업
    // 업체 생성
    @PostMapping
    public ResponseEntity<? extends CommonResponse> createCompany(
            @RequestBody CreateCompanyRequestDto requestDto) {

        return ResponseEntity.status(CREATE_COMPANY.getHttpStatus())
                .body(success(CREATE_COMPANY.getHttpStatus().value(),
                        CREATE_COMPANY.getMessage(),
                        companyService.createCompany(requestDto)));
    }

    // 업체 상세 조회
    @GetMapping("/{companyId}")
    public ResponseEntity<? extends CommonResponse> getCompany(
            @PathVariable("companyId") String companyId) {
        GetCompanyResponseDto responseDto = companyService.getCompany(companyId);

        return ResponseEntity.status(GET_COMPANY.getHttpStatus())
                .body(success(GET_COMPANY.getHttpStatus().value(),
                        GET_COMPANY.getMessage(), responseDto));
    }

    // 모든 업체 조회
    @GetMapping
    public ResponseEntity<? extends CommonResponse> getAllCompany(
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {

        if (size != 10 && size != 30 && size != 50) {
            size = 10;
        }

        Sort.Direction sortDirection = Sort.Direction.fromString(direction);
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));

        Page<GetCompanyResponseDto> responseDto = companyService.getAllCompany(pageable);

        return ResponseEntity.status(GET_COMPANY.getHttpStatus())
                .body(success(GET_COMPANY.getHttpStatus().value(),
                        GET_COMPANY.getMessage(), responseDto));
    }

    // 업체 수정
    @PutMapping("/{companyId}")
    public ResponseEntity<? extends CommonResponse> updateCompany(
            @PathVariable("companyId") String companyId,
            @RequestBody UpdateCompanyRequestDto requestDto) {

        UpdateCompanyResponseDto responseDto = companyService.updateCompany(requestDto, companyId);

        return ResponseEntity.status(UPDATE_COMPANY.getHttpStatus())
                .body(success(UPDATE_COMPANY.getHttpStatus().value(),
                        UPDATE_COMPANY.getMessage(), responseDto));
    }

    // 업체 삭제
    @DeleteMapping("/{companyId}")
    public ResponseEntity<? extends CommonResponse> deleteCompany(
            @PathVariable("companyId") String companyId
    ) {
        Long userId = null;
        DeleteCompanyResponseDto responseDto = companyService.deleteCompany(companyId, userId);

        return ResponseEntity.status(DELETE_COMPANY.getHttpStatus())
                .body(success(DELETE_COMPANY.getHttpStatus().value(),
                        DELETE_COMPANY.getMessage(), responseDto));
    }

    // 업체 검색
    @GetMapping("/search")
    public ResponseEntity<? extends CommonResponse> searchCompany(
            @RequestParam("keyword") String keyword, @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {

        if (size != 10 && size != 30 && size != 50) {
            size = 10;
        }

        Sort.Direction sortDirection = Sort.Direction.fromString(direction);
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));

        Page<GetCompanyResponseDto> responseDto = companyService.searchCompany(keyword,
                pageRequest);

        return ResponseEntity.status(SEARCH_COMPANY.getHttpStatus())
                .body(success(SEARCH_COMPANY.getHttpStatus().value(),
                        SEARCH_COMPANY.getMessage(), responseDto));
    }
}
