package com.hs.blog.controller;


import com.hs.blog.domain.ArticleMessageParam;
import com.hs.blog.service.ArticleMessageService;
import com.hs.blog.service.ArticleMessageServiceImpl;
import com.hs.blog.domain.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArticleMessageController {

    @Autowired
    private ArticleMessageService articleMessageService;

    @GetMapping("/message/increment")
    public void incrementViewCount(@RequestParam ArticleMessageParam articleMessageParam){
        articleMessageService.sendRefreshViewCount(articleMessageParam);
    }

    @GetMapping("/message/refresh-blog-edited")
    public void refreshBlogEdited(@RequestParam ArticleMessageParam articleMessageParam){
        articleMessageService.sendRefreshBlogEdited(articleMessageParam);
    }

}
