//package com.hs.blog.service;
//
//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import com.hs.blog.dao.mapper.ArticleMapper;
//import com.hs.blog.dao.pojo.Article;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import javax.annotation.Resource;
//import java.util.List;
//
//@Component
//public class ViewCountService {
//
//    @Autowired
//    private ArticleMapper articleMapper;
//
//    @PostConstruct
//    public void initViewCount(){
//        //为了 保证 启动项目的时候，redis中的浏览量 如果没有，读取数据库的数据，进行初始化
//        //便于更新的时候 自增
//        List<Article> articles = articleMapper.selectList(new LambdaQueryWrapper<>());
//        for (Article article : articles) {
//            String viewCountStr = (String) redisTemplate.opsForHash().get("view_count", String.valueOf(article.getId()));
//            if (viewCountStr == null){null
//                //初始化
//                redisTemplate.opsForHash().put("view_count", String.valueOf(article.getId()),String.valueOf(article.getViewCounts()));
//            }
//        }
//    }
//
//    @Autowired
//    private StringRedisTemplate redisTemplate;
//
//}
