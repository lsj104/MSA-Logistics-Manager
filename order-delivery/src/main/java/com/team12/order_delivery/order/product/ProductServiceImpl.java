package com.team12.order_delivery.order.product;

import com.team12.order_delivery.order.company.CompanyProductClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final CompanyProductClient companyProductClient;

    @Override
    public ProductResponseDto getProduct(String productId) {
        return companyProductClient.fetchProductById(productId).data();
    }

}
