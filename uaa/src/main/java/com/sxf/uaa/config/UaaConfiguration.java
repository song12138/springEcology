package com.sxf.uaa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

//https://www.jianshu.com/p/eb7920fc2778
//https://www.cnblogs.com/xingxueliao/p/5911292.html
//https://blog.csdn.net/DuShiWoDeCuo/article/details/78874354

/**
 * Oauth2配置类AuthorizationServerConfigurerAdapter
 */
@Configuration
@EnableAuthorizationServer
public class UaaConfiguration extends AuthorizationServerConfigurerAdapter {


    //配置OAuth2的客户端相关信息
    //用来配置客户端详情服务
    //clientId：（必须的）用来标识客户的Id。
    //secret：（需要值得信任的客户端）客户端安全码，如果有的话。
    //scope：用来限制客户端的访问范围，如果为空（默认）的话，那么客户端拥有全部的访问范围。
    //authorizedGrantTypes：此客户端可以使用的授权类型，默认为空。
    //authorities：此客户端可以使用的权限（基于Spring Security authorities）。
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    }



    //用来配置令牌端点(Token Endpoint)的安全约束.
    //对应于配置AuthorizationServer安全认证的相关信息，创建ClientCredentialsTokenEndpointFilter核心过滤器
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
            // 开启/oauth/token_key验证端口无权限访问
            .tokenKeyAccess("permitAll()")
            // 开启/oauth/check_token验证端口认证权限访问
            .checkTokenAccess("isAuthenticated()");
    }

    //配置身份认证器，配置认证方式，TokenStore，TokenGranter，OAuth2RequestFactory
    //用来配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services)。
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

    }

    /**
     * 使用非对称加密算法来对Token进行签名
     * @return
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {

        final JwtAccessTokenConverter converter = new JwtAccessToken();
        // 导入证书
        KeyStoreKeyFactory keyStoreKeyFactory =
                new KeyStoreKeyFactory(new ClassPathResource("keystore.jks"), "password".toCharArray());
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair("selfsigned"));

        return converter;
    }

}
