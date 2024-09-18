package com.team12.order_delivery.delivery.client;

import com.team12.common.dto.slack.SlackRequestDto;
import com.team12.common.exception.response.SuccessResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="slack", url="http://localhost:8082/slack/")
public interface SlackClient {
    @PostMapping("")
    SuccessResponse<?> sendMessage(@RequestBody SlackRequestDto request);
}
