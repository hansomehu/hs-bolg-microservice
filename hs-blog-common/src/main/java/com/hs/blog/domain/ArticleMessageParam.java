package com.hs.blog.domain;


import com.hs.blog.entity.Article;
import lombok.Data;


/**
 *  this domain class is only for pass parameters in Message Service
 */
@Data
public class ArticleMessageParam {
    private long articleId;

    private String hash_key;

    private Article article;
}
