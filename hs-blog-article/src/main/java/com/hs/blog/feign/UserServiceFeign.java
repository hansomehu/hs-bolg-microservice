package com.hs.blog.feign;


import com.hs.blog.entity.SysUser;
import com.hs.blog.vo.UserVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("USER-SERVICE")
public interface UserServiceFeign {

    @GetMapping("/user-service/service/user/find_user_by_id")
    public SysUser findUserById(@RequestParam("id") Long id);

    @GetMapping("/user-service/find-user-by-username")
    public SysUser findUserByUsername(@RequestParam("username") String username);

    @GetMapping("/find-uservo-by-id")
    public UserVo findUserVoById(@RequestParam("id") Long id);
}
