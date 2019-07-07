package com.coolron.security.app;

import com.coolron.security.core.properties.OAuth2ClientProperties;
import com.coolron.security.core.properties.SecurityProperties;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhailiang
 * 服务提供商 --- 认证服务器
 * 1. 授权码模式:
 *    获取授权码  GET
 *    http://localhost/oauth/authorize?response_type=code&client_id=imooc&redirect_uri=http://example.com&scope=all
 *    获取token  POST
 *    http://localhost:80/oauth/token?grant_type=authorization_code&code=ZFV2eS&redirect_uri=http://example.com&scope=all
 *    请求头 :
 *    Authorization 指定用户名和密码(配置文件中配置的security.oauth2.client.client-id 和 security.oauth2.client.client-secret)
 *
 * 2. 密码模式:
 *   获取token  POST
 *   http://localhost:80/oauth/token?grant_type=password&scope=all&username=admin&password=123456
 *   请求头 :
 *   Authorization 指定用户名和密码(配置文件中配置的security.oauth2.client.client-id 和 security.oauth2.client.client-secret)
 *
 *   测试: 使用token获取用户列表
 *  获取用户列表 : localhost:80/user/getUserList
 *  需要在头中加 : 获取token信息中的
 *  "access_token": "dfd655c7-8967-412d-b7ac-09685c799be7",
 *  "token_type": "bearer",
 *   Authorization : bearer dfd655c7-8967-412d-b7ac-09685c799be7
 */

//  基本的Token的参数配置：
@Configuration
@EnableAuthorizationServer
public class CoolronAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    // 存取令牌   在TokenStoreConfig中注入
	@Autowired
	private TokenStore tokenStore;

	// 密签加密转换器
    @Autowired(required = false)
	private JwtAccessTokenConverter jwtAccessTokenConverter;

    // 扩展信息
    @Autowired(required = false)
    private TokenEnhancer jwtTokenEnhancer;

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * @param endpoints  入口点
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore)
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService);

        if(jwtAccessTokenConverter != null && jwtTokenEnhancer != null){

            TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
            List<TokenEnhancer> enhancers = new ArrayList<>();
            enhancers.add(jwtTokenEnhancer);
            enhancers.add(jwtAccessTokenConverter);
            enhancerChain.setTokenEnhancers(enhancers);
            endpoints.tokenEnhancer(enhancerChain)
                    .accessTokenConverter(jwtAccessTokenConverter);
        }
    }

    /**
     * 客户端 第三方应用
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        InMemoryClientDetailsServiceBuilder builder = clients.inMemory();
        if(ArrayUtils.isNotEmpty(securityProperties.getOauth2().getClients())){
            for (OAuth2ClientProperties client : securityProperties.getOauth2().getClients()) {
                builder.withClient(client.getClientId())
                        .secret(client.getClientSecret())
                        .authorizedGrantTypes("password","refresh_token")
                        .accessTokenValiditySeconds(client.getAccessTokenValidateSeconds())  // token过期时间
                        .refreshTokenValiditySeconds(2592000)  // refresh_token的过期时间(一般设置较长)
                        .scopes("all");
            }
        }


        // 测试 http://localhost:80/oauth/token?grant_type=password&scope=all&username=admin&password=123456
       /* clients.inMemory() // 内存
                .withClient("coolron")  // clientID
                .secret("coolronSecurityKey")
                .accessTokenValiditySeconds(3600)  // 令牌的有效时间 秒
                .authorizedGrantTypes("password","refresh_token")  // coolron 这个用户支持的授权模式有哪些  String[]
                .scopes("all","read","write");  // 发出的权限有哪些 可以配多个  String[]
                //.and()
                //.withClient("ron")  可以给多个 client 发送
         */
    }
}
