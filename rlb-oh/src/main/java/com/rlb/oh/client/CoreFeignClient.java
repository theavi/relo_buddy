package com.rlb.oh.client;


import com.core.dto.TeamDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient("rlb-core")
public interface CoreFeignClient {

    @GetMapping("/getTeam")
    public TeamDto getTeam(@RequestParam("pincode") Integer pincode);
}
