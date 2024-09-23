package com.team12.company_product.company.controller;

import static com.team12.common.exception.response.SuccessResponse.success;
import static com.team12.company_product.company.message.SuccessMessage.CREATE_COMPANY;
import static com.team12.company_product.company.message.SuccessMessage.DELETE_COMPANY;
import static com.team12.company_product.company.message.SuccessMessage.GET_COMPANY;
import static com.team12.company_product.company.message.SuccessMessage.SEARCH_COMPANY;
import static com.team12.company_product.company.message.SuccessMessage.UPDATE_COMPANY;

import com.team12.common.customPage.CustomPageResponse;
import com.team12.common.exception.response.SuccessResponse;
import com.team12.company_product.company.dto.request.CreateCompanyRequestDto;
import com.team12.company_product.company.dto.response.CreateCompanyResponseDto;
import com.team12.company_product.company.dto.response.DeleteCompanyResponseDto;
import com.team12.company_product.company.dto.response.GetCompanyResponseDto;
import com.team12.company_product.company.dto.request.UpdateCompanyRequestDto;
import com.team12.company_product.company.dto.response.UpdateCompanyResponseDto;
import com.team12.company_product.company.service.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "업체", description = "업체 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/companies")
public class CompanyController {

    private final CompanyService companyService;

    @Operation(summary = "업체 생성")
    @PostMapping
    public ResponseEntity<SuccessResponse<CreateCompanyResponseDto>> createCompany(
            @RequestHeader("X-User-Id") Long userId,
            @RequestBody CreateCompanyRequestDto requestDto) {

        return ResponseEntity.status(CREATE_COMPANY.getHttpStatus())
                .body(success(CREATE_COMPANY.getHttpStatus().value(),
                        CREATE_COMPANY.getMessage(),
                        companyService.createCompany(requestDto, userId)));
    }

    @Operation(summary = "업체 상세 조회", description = "업체ID로 업체를 상세 조회하는 API입니다.")
    @GetMapping("/{companyId}")
    public ResponseEntity<SuccessResponse<GetCompanyResponseDto>> getCompany(
            @PathVariable("companyId") String companyId) {
        GetCompanyResponseDto responseDto = companyService.getCompany(companyId);

        return ResponseEntity.status(GET_COMPANY.getHttpStatus())
                .body(success(GET_COMPANY.getHttpStatus().value(),
                        GET_COMPANY.getMessage(), responseDto));
    }

    @Operation(summary = "모든 업체 조회")
    @GetMapping
    public ResponseEntity<SuccessResponse<CustomPageResponse<GetCompanyResponseDto>>> getAllCompany(
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<GetCompanyResponseDto> responseDto = companyService.getAllCompany(pageable);

        CustomPageResponse<GetCompanyResponseDto> customResponse = new CustomPageResponse<>(
                responseDto);

        String message = String.format("%d개의 업체 조회가 완료되었습니다.", responseDto.getTotalElements());

        return ResponseEntity.status(GET_COMPANY.getHttpStatus())
                .body(success(GET_COMPANY.getHttpStatus().value(), message, customResponse));
    }

    @Operation(summary = "업체 수정")
    @PutMapping("/{companyId}")
    public ResponseEntity<SuccessResponse<UpdateCompanyResponseDto>> updateCompany(
            @PathVariable("companyId") String companyId,
            @RequestBody UpdateCompanyRequestDto requestDto,
            @RequestHeader("X-User-Id") Long userId
    ) {

        UpdateCompanyResponseDto responseDto = companyService.updateCompany(requestDto, companyId,
                userId);

        return ResponseEntity.status(UPDATE_COMPANY.getHttpStatus())
                .body(success(UPDATE_COMPANY.getHttpStatus().value(),
                        UPDATE_COMPANY.getMessage(), responseDto));
    }

    @Operation(summary = "업체 삭제")
    @DeleteMapping("/{companyId}")
    public ResponseEntity<SuccessResponse<DeleteCompanyResponseDto>> deleteCompany(
            @PathVariable("companyId") String companyId,
            @RequestHeader("X-User-Id") Long userId
    ) {
        DeleteCompanyResponseDto responseDto = companyService.deleteCompany(companyId, userId);

        return ResponseEntity.status(DELETE_COMPANY.getHttpStatus())
                .body(success(DELETE_COMPANY.getHttpStatus().value(),
                        DELETE_COMPANY.getMessage(), responseDto));
    }

    @Operation(summary = "업체 검색", description = "업체 이름으로 검색, ID로 검색할 수 있는 API입니다.")
    @GetMapping("/search")
    public ResponseEntity<SuccessResponse<CustomPageResponse<GetCompanyResponseDto>>> searchCompany(
            @RequestParam("keyword") String keyword,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<GetCompanyResponseDto> responseDto = companyService.searchCompany(keyword, pageable);

        CustomPageResponse<GetCompanyResponseDto> customResponse = new CustomPageResponse<>(
                responseDto);

        return ResponseEntity.status(SEARCH_COMPANY.getHttpStatus())
                .body(success(SEARCH_COMPANY.getHttpStatus().value(),
                        SEARCH_COMPANY.getMessage(), customResponse));
    }
}
