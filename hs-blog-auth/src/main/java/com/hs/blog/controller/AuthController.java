package com.hs.blog.controller;


import com.hs.blog.entity.OauthDto;
import com.hs.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Map;


/**
 *      关于Token的发放post、更新refresh和校验check API说明
 *      1. 请求token需要的参数：client_id/secret grant_type username password
 *         the gateway will pack the parameters above to request token automatically
 *         if you're request a token manually, remember to pack these parameters
 *      2. check token only need you to send token to /oauth/check_token
 *         it will return you with all the information in the token(including username and
 *         other info you put into the token through a token enhancer)
 *
 */
@RestController
@RequestMapping("/oauth")
public class AuthController {

    @Resource
    private TokenEndpoint tokenEndpoint;

    @Autowired
    private ConsumerTokenServices consumerTokenServices;


    /**
     *
     * @param principal
     * @param parameters including but not limited: client_id/secret | grant_type=password | username | password
     * @return
     * @throws HttpRequestMethodNotSupportedException
     */
//    @PostMapping("/token")
//    public Result postAccessToken(Principal principal, @RequestParam Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {
//
//        OAuth2AccessToken oAuth2AccessToken = tokenEndpoint.postAccessToken(principal, parameters).getBody();
//        OauthDto oauthDto = OauthDto.builder()
//                .token(oAuth2AccessToken.getValue())
//                .refreshToken(oAuth2AccessToken.getRefreshToken().getValue())
//                .expiresIn(oAuth2AccessToken.getExpiresIn())
//                .tokenHead("bearer").build();
//
//        return new Result(true, 200, "ok", oauthDto);
//    }

    @PostMapping("/customLogout")
    public Result logout(@RequestParam("token") String t){
        if (consumerTokenServices.revokeToken(t)) {
            return new Result(true, 200, "logout success", null);
        }
        return Result.fail(400, "logout fail");
    }


    /**
     * receive token and return the username information
     *
     * @param token
     * @return
     */
    @PostMapping("/parse_for_username")
    public String parse(@RequestParam String token){
        Map<String, Object> map = (Map<String, Object>)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = map.get("username").toString();


        return username;
    }

    @GetMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication){
        new SecurityContextLogoutHandler().logout(request, response, authentication);

    }
}
