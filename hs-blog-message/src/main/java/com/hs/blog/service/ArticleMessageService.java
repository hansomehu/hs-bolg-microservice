package com.hs.blog.service;

import com.hs.blog.domain.ArticleMessageParam;

public interface ArticleMessageService {
    void sendRefreshViewCount(ArticleMessageParam articleMessageParam);
    void sendRefreshBlogEdited(ArticleMessageParam articleMessageParam);
}
