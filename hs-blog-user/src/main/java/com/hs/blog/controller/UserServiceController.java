package com.hs.blog.controller;


import com.hs.blog.entity.Authority;
import com.hs.blog.entity.SysUser;
import com.hs.blog.service.SysUserService;
import com.hs.blog.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("user-service")
public class UserServiceController {

    @Autowired
    private SysUserService sysUserService;

    @GetMapping("/find-user-by-id")
    public SysUser findUserById(@RequestParam("id") Long id){
        return sysUserService.findUserById(id);
    }

    @GetMapping("/find-uservo-by-id")
    public UserVo findUserVoById(@RequestParam("id") Long id){
        return sysUserService.findUserVoById(id);
    }

    @GetMapping("/find-user-by-username")
    public SysUser findUserByUsername(@RequestParam("username") String username){
        return sysUserService.findUserByUsername(username);
    }
    @GetMapping("/find-authorities-by-userid")
    public Set<Authority> findAuthoritiesByUserId(@RequestParam("userid") Long id){
        return sysUserService.findAuthoritiesByUserId(id);
    }

}
