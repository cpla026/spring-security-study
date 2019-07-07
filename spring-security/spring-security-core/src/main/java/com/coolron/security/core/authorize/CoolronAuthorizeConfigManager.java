package com.coolron.security.core.authorize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Created by Administrator on 2018/11/10.
 * 配置管理器
 *
 * 到browser 中去指定
 */
@Component("coolronAuthorizeConfigManager")
public class CoolronAuthorizeConfigManager implements AuthorizeConfigManager {

    // 存储公共的权限 和 自定义的权限 (都实现了 AuthorizeConfigProvider 接口)
    @Autowired
    private Set<AuthorizeConfigProvider> authorizeConfigProviders;

    /**
     *
     * @param conf
     */
    @Override
    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry conf) {
        for (AuthorizeConfigProvider authorizeConfigProvider : authorizeConfigProviders) {
            authorizeConfigProvider.config(conf);
        }
        conf.anyRequest().authenticated(); // 所有请求都需要身份认证才可以访问
    }
}
