package com.team12.company_product.company.hub;

import com.team12.common.exception.response.SuccessResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "hub")
public interface HubClient {

    @GetMapping("/api/hubs/{hubId}/check")
    SuccessResponse<?> checkHub(@PathVariable("hubId") UUID hubId);

}