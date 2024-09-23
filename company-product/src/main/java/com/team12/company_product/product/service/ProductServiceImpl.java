package com.team12.company_product.product.service;


import com.team12.company_product.company.domain.Company;
import com.team12.company_product.company.exception.CompanyException;
import com.team12.company_product.company.hub.HubClient;
import com.team12.company_product.company.service.CompanyService;
import com.team12.company_product.product.domain.Product;
import com.team12.company_product.product.dto.request.CreateProductRequestDto;
import com.team12.company_product.product.dto.request.UpdateProductRequestDto;
import com.team12.company_product.product.dto.response.CreateProductResponseDto;
import com.team12.company_product.product.dto.response.DeleteProductResponseDto;
import com.team12.company_product.product.dto.response.GetProductResponseDto;
import com.team12.company_product.product.dto.response.UpdateProductResponseDto;
import com.team12.company_product.product.exception.ExceptionMessage;
import com.team12.company_product.product.exception.ProductException;
import com.team12.company_product.product.repository.ProductRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final CompanyService companyService;

    private final HubClient hubClient;


    // 상품 생성
    @Transactional
    public CreateProductResponseDto createProduct(CreateProductRequestDto requestDto, Long userId) {

        Company company = null;
        if (requestDto.companyId() != null) {
            company = companyService.findById(requestDto.companyId());
        }

        checkHubIfPresent(requestDto.hubId());

        Product product = Product.of(requestDto, company, userId);
        product.setCreatedBy(userId);
        product.setUpdatedBy(userId);
        productRepository.save(product);

        return CreateProductResponseDto.from(product);
    }


    // 상품 상세 조회
    @Transactional(readOnly = true)
    public GetProductResponseDto getProduct(String productId) {

        Product product = findById(productId);
        return GetProductResponseDto.from(product);
    }

    // 모든 상품 조회
    @Transactional(readOnly = true)
    public Page<GetProductResponseDto> getAllProduct(Pageable pageable) {
        Page<Product> products = productRepository.findByIsDeletedFalse(pageable);
        return products.map(GetProductResponseDto::from);
    }

    // 상품 수정
    @Transactional
    public UpdateProductResponseDto updateProduct(UpdateProductRequestDto requestDto,
            String productId, Long userId) {

        Company company = null;
        if (requestDto.companyId() != null) {
            company = companyService.findById(requestDto.companyId());
        }

        checkHubIfPresent(requestDto.hubId());

        Product product = findById(productId);
        product.setUpdatedBy(userId);
        product.update(requestDto, company);
        return UpdateProductResponseDto.from(product);
    }

    // 상품 삭제
    @Transactional
    public DeleteProductResponseDto deleteProduct(String productId, Long userId) {

        Product product = findById(productId);
        product.delete(userId);

        return DeleteProductResponseDto.from(product);

    }

    // 상품 검색
    @Transactional(readOnly = true)
    public Page<GetProductResponseDto> searchProduct(String keyword, Pageable pageable) {
        // 키워드가 없는 경우
        if (keyword == null || keyword.isEmpty()) {
            throw new ProductException(
                    ExceptionMessage.KEYWORD_EMPTY);
        }

        Page<Product> productPage = Page.empty();

        if (keyword.startsWith("name:")) {
            // 이름 검색
            String nameKeyWord = keyword.replaceFirst("name:", "").trim();
            productPage = productRepository.findByProductNameContaining(nameKeyWord, pageable);
        } else if (keyword.startsWith("id:")) {
            // ID 검색
            String idKeyword = keyword.replaceFirst("id:", "").trim();
            productPage = productRepository.findByProductIdContaining(idKeyword, pageable);
        }

        if (productPage.isEmpty()) {
            throw new ProductException(ExceptionMessage.KEYWORD_NOT_PROVIDED);
        }

        return productPage.map(GetProductResponseDto::from);
    }

    // 상품 재고 차감
    @Transactional
    @Override
    public void reduceProductQuantity(String productId, Long quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductException(ExceptionMessage.PRODUCT_NOT_FOUND));

        if (product.getQuantity() < quantity) {
            throw new ProductException(ExceptionMessage.INSUFFICIENT_PRODUCT_QUANTITY);
        }

        product.reduceQuantity(quantity);
        productRepository.save(product);
    }


    // 상품 재고 수정
    @Override
    public void updateProductQuantity(String productId, Long quantityChange) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductException(ExceptionMessage.PRODUCT_NOT_FOUND));

        Long updatedQuantity = product.getQuantity() + quantityChange;

        if (updatedQuantity < 0) {
            throw new ProductException(ExceptionMessage.INSUFFICIENT_PRODUCT_QUANTITY);
        }

        product.setQuantity(updatedQuantity);

        productRepository.save(product);
    }


    // ID로 상품 검색
    @Transactional
    public Product findById(String productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductException(ExceptionMessage.PRODUCT_NOT_FOUND));
    }


    // 허브 검증
    private void checkHubIfPresent(UUID hubId) {
        if (hubId != null) {
            try {
                hubClient.checkHub(hubId);
            } catch (Exception e) {
                throw new CompanyException(
                        com.team12.company_product.company.exception.ExceptionMessage.HUB_NOT_FOUND);
            }
        }
    }

}
