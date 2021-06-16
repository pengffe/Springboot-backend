package com.sj.pte.modules.authServer.config;/**
 * Created by TUTEHUB on 2021-06-10 13:38.
 * Copyright © 2021 TUTEHUB. All rights reserved.
 * ------------------------
 * Non-disclosure Terms
 * -------------------------
 * These codes are TUTEHUB's property, as the developer, such as employee, contractor, and intern, working for TUTEHUB cannot disclose, spread, copy, preserve, sell, and do other activities without the consent of TUTEHUB.
 * Developer fengpeng agrees with above terms.
 * Technique Support: jobyme88.com
 */

/**
 * @descrption
 */

import com.sj.pte.modules.authServer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;


@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    @Qualifier("authenticationManager")
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    @Qualifier("jwtTokenStore")
    private TokenStore tokenStore;

    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;


    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                //客户端ID
                .withClient("client")
                //密钥
                .secret(passwordEncoder.encode("123456"))
                //重定向地址
                .redirectUris("http://www.baidu.com")
                //授权范围
                .scopes("all")
                .accessTokenValiditySeconds(60 * 3)
                .refreshTokenValiditySeconds(60 * 60 * 24 * 3)
                /**
                 * 授权类型
                 * authorization_code: 授权码模式
                 * password: 密码模式
                 * refresh_token: 刷新令牌
                 */
                .authorizedGrantTypes("authorization_code", "password", "refresh_token");

    }

    //http://localhost:8081/api/v1/oauth/authorize?client_id=client&response_type=code&redirect_uri=http://www.baidu.com&scope=all

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userService)
                //accessToken 转成 jwtToken
                .tokenStore(tokenStore)
                .accessTokenConverter(jwtAccessTokenConverter);
    }

//
//    @Override
//    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
//        oauthServer.realm(RESOURCE_ID).allowFormAuthenticationForClients();
//    }
}
