package com.team12.company_product.company.repository;

import com.team12.company_product.company.domain.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, String> {

    Page<Company> findByCompanyNameContaining(String companyName, Pageable pageable);

    Page<Company> findByCompanyIdContaining(String companyId, Pageable pageable);
}
