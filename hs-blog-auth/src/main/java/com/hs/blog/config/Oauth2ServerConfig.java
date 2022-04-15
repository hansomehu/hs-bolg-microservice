package com.hs.blog.config;

import com.hs.blog.entity.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;

import javax.sql.DataSource;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;


@Configuration
@EnableAuthorizationServer
public class Oauth2ServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private ClientDetailsService clientDetailsService;
    @Autowired
    private JwtTokenEnhancer jwtTokenEnhancer;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        /**
         *  use in memory client info
         */
//        clients.inMemory()
//                .withClient("gateway-app")
//                .secret(passwordEncoder.encode("123456"))
//                .scopes("all")
//                .authorizedGrantTypes("password", "refresh_token")
//                .accessTokenValiditySeconds(3600*24)
//                .refreshTokenValiditySeconds(3600*24*7);


        /**
         *  Use database to store client info
         *  !!! This is the quickest way of using database store client info
         *      Since the ClientDetailsService below is using the framework provided db fetch mechanism
         *      So it is of great importance to build tables in related database strictly follow the official guide
         */
        clients.withClientDetails(clientDetailsService);


    }

//    @Bean
//    public ClientDetailsService clientDetailsService(@Qualifier("auth-client-datasource") DataSource dataSource) {
//        ClientDetailsService clientDetailsService = new JdbcClientDetailsService(dataSource);
//        ((JdbcClientDetailsService) clientDetailsService).setPasswordEncoder(passwordEncoder);
//        return clientDetailsService;
//    }

    // 端点的服务设置
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> delegates = new ArrayList<>();
        delegates.add(jwtTokenEnhancer);
        delegates.add(accessTokenConverter());
        tokenEnhancerChain.setTokenEnhancers(delegates);
        endpoints.authenticationManager(authenticationManager)
                .accessTokenConverter(accessTokenConverter())
                .userDetailsService(userDetailsService)
                .tokenEnhancer(tokenEnhancerChain)
                .allowedTokenEndpointRequestMethods(HttpMethod.POST);
    }

    /**
     * In memory user config
     * Enable this if using memory to do quick test
     */
//    @Bean
//    public UserDetailsService userDetailsService(){
//        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
//        inMemoryUserDetailsManager.createUser(User.withUsername("handsomehu").password(passwordEncoder.encode("123456")).roles("admin").build());
//        SecurityUser user = new SecurityUser();
//        user.setUsername("handsomehu");
//        user.setPassword(passwordEncoder.encode("123456"));
//        user.isAccountNonLocked();
//        SimpleGrantedAuthority admin = new SimpleGrantedAuthority("admin");
//        Collection<SimpleGrantedAuthority> simpleGrantedAuthorities = new HashSet<>();
//        simpleGrantedAuthorities.add(admin);
//        user.setAuthorities(simpleGrantedAuthorities);
//        inMemoryUserDetailsManager.createUser(user);
//        return inMemoryUserDetailsManager;
//
//    }


    // 令牌服务访问端点的安全约束，允许gateway发来的验证请求
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients() // form login check for applying a token
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("permitAll()");

    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setKeyPair(keyPair());

        return jwtAccessTokenConverter;
    }

    @Bean
    public KeyPair keyPair() {
        //从classpath下的证书中获取秘钥对
        //keytool -genkey -alias hsblog -keyalg RSA -keypass 123456 -keystore oauth2.jks -storepass 123456
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("oauth2.jks"), "123456".toCharArray());
        return keyStoreKeyFactory.getKeyPair("hsblog", "123456".toCharArray());
    }

}
