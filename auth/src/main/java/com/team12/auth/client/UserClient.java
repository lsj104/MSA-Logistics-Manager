package com.team12.auth.client;

import com.team12.common.dto.user.UserDetailsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "user-service", url = "http://localhost:8081")
public interface UserClient {
    @GetMapping("/client/login")
    UserDetailsDto getUserDetails(@RequestParam String username);
}
