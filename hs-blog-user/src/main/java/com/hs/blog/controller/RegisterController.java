package com.hs.blog.controller;

import com.hs.blog.entity.SysUser;
import com.hs.blog.service.LoginService;
import com.hs.blog.vo.Result;
import com.hs.blog.vo.params.OAuthLoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-service")
public class RegisterController {

    /**
     *  The logic behind user login under Oauth2 framework
     *  1. front end pass user input(username and password) to backend
     *  2. check username and password, then load user info from database
     *  3. front end send token request to /oauth/token, if success, return token to user
     *  If step 2 and 3 all passed
     *  4. back end return user info to front end
     */

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public Result login(@RequestBody OAuthLoginParam loginParam){
        return loginService.login(loginParam);

    }

    @PostMapping("/register")
    public Result register(@RequestBody OAuthLoginParam loginParam){
        //sso 单点登录，后期如果把登录注册功能 提出去（单独的服务，可以独立提供接口服务）
        return loginService.register(loginParam);
    }
}
