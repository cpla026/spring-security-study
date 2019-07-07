package com.coolron.security.app;

import com.coolron.security.app.jwt.CoolronJwtTokenEnhancer;
import com.coolron.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * @author zhailiang
 *
 * 测试 : http://localhost:80/oauth/token?grant_type=password&scope=all&username=admin&password=123456
 *
 * 刷新令牌:
 * http://localhost:80/oauth/token?grant_type=refresh_token&scope=all&refresh_token=(请求token的时候获取的token)
 *
 */
@Configuration
public class TokenStoreConfig {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    /**
     * TokenStore 负责令牌的存取
     * @return
     */
    /*@Bean
    public TokenStore redisTokenStore() {
        return new RedisTokenStore(redisConnectionFactory);
    }*/

    @Bean
    // 配置文件中有coolron.security.oauth2.tokenStore = redis 生效
    @ConditionalOnProperty(prefix = "coolron.security.oauth2", name = "tokenType", havingValue = "redis")
    public TokenStore redisTokenStore() {
        return new RedisTokenStore(redisConnectionFactory);
    }
    /**
     *  matchIfMissing = true没有配置项也生效
     */
    @Configuration
    // matchIfMissing = true 配置文件没有coolron.security.oauth2.tokenStore = jwt 下面也生效
    @ConditionalOnProperty(prefix = "coolron.security.oauth2", name = "tokenType", havingValue = "jwt", matchIfMissing = true)
    public static class JwtConfig {

        @Autowired
        private SecurityProperties securityProperties;

        @Bean
        public TokenStore jwtTokenStore() {
            return new JwtTokenStore(jwtAccessTokenConverter());
        }

        @Bean
        public JwtAccessTokenConverter jwtAccessTokenConverter() {
            JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
            //指定密签
            converter.setSigningKey(securityProperties.getOauth2().getJwtSigningKey());
            return converter;
        }

		@Bean
        // 默认的  业务系统可以覆盖
		@ConditionalOnMissingBean(name = "jwtTokenEnhancer")
		public TokenEnhancer jwtTokenEnhancer() {
			return new CoolronJwtTokenEnhancer();
		}

    }
}

