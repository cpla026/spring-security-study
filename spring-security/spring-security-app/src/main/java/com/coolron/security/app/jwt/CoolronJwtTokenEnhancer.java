package com.coolron.security.app.jwt;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

/**
 * 向accessToken中存放自定义的信息
 * Token的增强拓展类
 * @author zhailiang
 *
 */
public class CoolronJwtTokenEnhancer implements TokenEnhancer {

    /**
     *
     * @param accessToken   token
     * @param authentication  认证信息
     * @return
     */
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        // 存放需要往accessToken 中放的东西
        Map<String, Object> info = new HashMap<>();
        info.put("rose", "ron");
        info.put("company", "coolron");

        // 设置附加信息
        ((DefaultOAuth2AccessToken)accessToken).setAdditionalInformation(info);

        return accessToken;
    }

}

