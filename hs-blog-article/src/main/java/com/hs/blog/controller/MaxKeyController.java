package com.hs.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("maxKey")
public class MaxKeyController {
    //669240069155979264
    //pp2kMTcxMjIwMjExNTIyMTE5OTkHNE

    @RequestMapping("login")
    public String login(){

        String url = "http://localhost:8080/maxkey/authz/oauth/v20/authorize?client_id=669240069155979264&response_type=code&redirect_uri=http://localhost:8888/maxKey/code";
        return "redirect:"+url;
    }

    @RequestMapping("code")
    @ResponseBody
    public String code(String code){
        String url = "http://localhost:8080/maxkey/authz/oauth/v20/token?client_id=669240069155979264&client_secret=pp2kMTcxMjIwMjExNTIyMTE5OTkHNE&grant_type=authorization_code&redirect_uri=http://localhost:8888/maxKey/code&code="+code;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);
        System.out.println(forEntity.getStatusCode() + "："+forEntity.getBody());
        return "应该跳转应用首页 代表登录完成";
    }
}
