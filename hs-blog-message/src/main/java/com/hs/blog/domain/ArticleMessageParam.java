package com.hs.blog.domain;

import com.hs.blog.entity.Article;
import lombok.Data;

@Data
public class ArticleMessageParam {
    private long articleId;

    private String hashKey;

    private Article article;
}
