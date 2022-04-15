package com.hs.blog.feign;


import com.hs.blog.domain.ArticleMessageParam;
import com.hs.blog.vo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("MESSAGE-SERVICE")
public interface MessageServiceFeign {

    @GetMapping("/message/increment")
    public Result incrementViewCount(ArticleMessageParam articleMessageParam);

    @GetMapping("/message/refresh-blog-edited")
    public void refreshBlogEdited(@RequestParam ArticleMessageParam articleMessageParam);

}
