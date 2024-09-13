package com.team12.company_product.company.domain;

public enum CompanyType {
    PRODUCER("생산업체"),
    RECEIVER("수령업체");

    private final String description;

    CompanyType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
