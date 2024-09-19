package com.team12.common.enums;

import lombok.Getter;

@Getter
public enum UserRoleEnum {
    MASTER(Authority.MASTER),
    COMPANY(Authority.COMPANY),
    HUB_MANAGER(Authority.HUB_MANAGER),
    HUB_TO_HUB_DELIVERY(Authority.HUB_TO_HUB_DELIVERY),
    TO_COMPANY_DELIVERY(Authority.TO_COMPANY_DELIVERY);


    private final String authority;

    UserRoleEnum(String authority) {
        this.authority = authority;
    }

    public static class Authority {
        public static final String COMPANY = "ROLE_COMPANY";
        public static final String MASTER = "ROLE_MASTER";
        public static final String HUB_MANAGER = "ROLE_HUB_MANAGER";
        public static final String HUB_TO_HUB_DELIVERY = "ROLE_HUB_TO_HUB_DELIVERY";
        public static final String TO_COMPANY_DELIVERY = "ROLE_TO_COMPANY_DELIVERY";
    }
}
