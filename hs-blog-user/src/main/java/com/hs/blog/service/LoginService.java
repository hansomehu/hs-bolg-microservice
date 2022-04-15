package com.hs.blog.service;

import com.hs.blog.vo.Result;
import com.hs.blog.vo.params.OAuthLoginParam;

public interface LoginService {

    /**
     * login
     * @param loginParam
     * @return
     */
    Result login(OAuthLoginParam loginParam);

    /**
     * 注册
     * @param loginParam
     * @return
     */
    Result register(OAuthLoginParam loginParam);

    /**
     *  logout: front end send a refresh request to auth server then without returning new token to user
     *
     *  refresh_params: grant_type | refresh_token
     *  refresh_url: ip:port/oauth/refresh_token
     */
}
