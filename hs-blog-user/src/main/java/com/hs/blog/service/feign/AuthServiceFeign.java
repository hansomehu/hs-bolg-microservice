package com.hs.blog.service.feign;


import com.hs.blog.vo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Map;

@FeignClient("AUTH-SERVICE")
@Component
public interface AuthServiceFeign {

    @PostMapping("/token")
    public Result postAccessToken(@RequestParam Map<String, String> parameters);
}
