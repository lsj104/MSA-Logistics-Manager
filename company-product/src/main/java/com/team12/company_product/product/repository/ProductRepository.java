package com.team12.company_product.product.repository;

import com.team12.company_product.product.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {

    Page<Product> findByProductNameContaining(String productName, Pageable pageable);

    Page<Product> findByProductIdContaining(String productId, Pageable pageable);
}
