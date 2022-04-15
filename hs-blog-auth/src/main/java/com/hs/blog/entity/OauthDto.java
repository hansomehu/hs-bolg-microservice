package com.hs.blog.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Oauth2获取Token返回信息并封装进行该对象
 * 前端拿到该对象后可以快速地按需获取业务所需的信息
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class OauthDto {
    private String token;

    private String refreshToken;

    private String tokenHead;

    private int expiresIn;
}
