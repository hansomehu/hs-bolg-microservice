package com.hs.blog.service;


import com.hs.blog.constant.MessageConstant;
import com.hs.blog.domain.ArticleMessageParam;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleMessageServiceImpl implements ArticleMessageService{

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Override
    public void sendRefreshViewCount(ArticleMessageParam articleMessageParam) {
        rabbitTemplate.convertAndSend(MessageConstant.EXCHANGE_DIRECT, MessageConstant.ROUTING_KEY_REFRESH_VIEW, articleMessageParam);
    }

    @Override
    public void sendRefreshBlogEdited(ArticleMessageParam articleMessageParam) {
        rabbitTemplate.convertAndSend(MessageConstant.EXCHANGE_DIRECT, MessageConstant.ROUTING_KEY_REFRESH_EDITED, articleMessageParam);
    }
}
