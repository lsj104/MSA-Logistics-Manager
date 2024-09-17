package com.team12.order_delivery.order.company;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum CompanyType {
    PRODUCER("생산업체"),
    RECEIVER("수령업체");

    private final String description;

    CompanyType(String description) {
        this.description = description;
    }

}
