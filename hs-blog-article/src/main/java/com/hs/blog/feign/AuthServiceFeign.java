package com.hs.blog.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@FeignClient("AUTH-SERVICE")
public interface AuthServiceFeign {

    @RequestMapping({"/oauth/check_token"})
    @ResponseBody
    Map<String, ?> checkToken(@RequestParam("token") String value);


}
