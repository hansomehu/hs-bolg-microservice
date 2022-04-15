package com.hs.blog.service;

import com.hs.blog.vo.Result;
import com.hs.blog.vo.params.CommentParam;

public interface CommentsService {
    /**
     * 根据文章id 查询所有的评论列表
     * @param id
     * @return
     */
    Result commentsByArticleId(Long id);

    Result publishComment(CommentParam commentParam);
}
