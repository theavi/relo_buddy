package com.core.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("rlb-common")
public interface ProducerFeignClient {
    @GetMapping("/hello")
    public ResponseEntity<String> hello();
}
