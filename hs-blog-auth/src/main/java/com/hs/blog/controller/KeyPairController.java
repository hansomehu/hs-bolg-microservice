package com.hs.blog.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.KeyPair;

@RestController
@RequestMapping("test")
public class KeyPairController {

    @Autowired
    private KeyPair keyPair;

    @PostMapping("/1")
    public String test(){
        return "ok";
    }

}
