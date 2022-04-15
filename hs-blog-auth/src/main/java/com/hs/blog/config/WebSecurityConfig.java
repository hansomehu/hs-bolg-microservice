package com.hs.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     *      配置基本的访问拦截，如果要使用
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // EndpointRequest.toAnyEndpoint() 将请求与所有端点进行匹配，然后确保所有端点都具有ENDPOINT_ADMIN角色
                // 需要引入Actuator包
//                .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()

                // open authorization related API to all users
                .antMatchers("/oauth/**").permitAll()
                .and()
                .cors().disable();
    }

    /**
     * The oauth2 server only allow users to visit the default endpoints(APIs like /oauth/token../check_token) out
     * of security concern.
     * If you need to open your own endpoints(APIs) in this server app, then you need to add your endpoints(APIs) address
     * into 'ignoring' ant-matchers like bellow.
     *
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/oauth/customLogout","/oauth/logout");
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
