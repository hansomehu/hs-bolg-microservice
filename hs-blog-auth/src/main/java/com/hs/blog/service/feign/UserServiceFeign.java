package com.hs.blog.service.feign;


import com.hs.blog.entity.Authority;
import com.hs.blog.entity.SysUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@FeignClient("USER-SERVICE")
@Service
public interface UserServiceFeign {

    @GetMapping("/user-service/find-user-by-username")
    public SysUser findUserByUsername(@RequestParam("username") String username);

    @GetMapping("/find-authorities-by-userid")
    public Set<Authority> findAuthoritiesByUserId(@RequestParam("userid") Long id);
}
