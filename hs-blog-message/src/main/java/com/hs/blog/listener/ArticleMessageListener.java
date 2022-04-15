package com.hs.blog.listener;


import com.alibaba.fastjson.JSON;
import com.hs.blog.constant.MessageConstant;
import com.hs.blog.domain.ArticleMessageParam;
import com.hs.blog.entity.Article;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Set;

@Component
public class ArticleMessageListener {

    @Autowired
    private RedisTemplate redisTemplate;

    @RabbitListener(queues = MessageConstant.QUEUE_BLOG_REFRESH_VIEW)
    public void refreshViewCount(ArticleMessageParam articleMessageParam){
        //redisTemplate.opsForHash().increment("view_count",String.valueOf(article.getId()),1);
        Long response = redisTemplate.opsForHash().increment(articleMessageParam.getHashKey(), articleMessageParam.getArticleId(), 1);

//        if (response > 0) return new Result(true, response.intValue(), "increment ok", null);
//        return new Result(false, 400, "increment failed", null);
    }

    @RabbitListener(queues = MessageConstant.QUEUE_BLOG_REFRESH_EDITED)
    public void refreshBlogEdited(ArticleMessageParam articleMessageParam){

        Long articleId = articleMessageParam.getArticleId();
        String params = DigestUtils.md5Hex(articleId.toString());
        String redisKey = "view_article::ArticleController::findArticleById::" + params;
        Article article = articleMessageParam.getArticle();

        redisTemplate.opsForValue().set(redisKey, JSON.toJSONString(article), Duration.ofMillis(5 * 60 * 1000));
        // As for the update of the whole article list, delete the cached list is recommended
        // Simply push server to re-cache all the article list is the quickest way to make the list be up-to-date

        // However, for a huge amount of data this way is not recommended
        // Instead we have to alter the cache for this particular article precisely
        Set<String> keys = redisTemplate.keys("listArticle*");
        keys.forEach(key -> {
            redisTemplate.delete(key);
        });

    }

    }



