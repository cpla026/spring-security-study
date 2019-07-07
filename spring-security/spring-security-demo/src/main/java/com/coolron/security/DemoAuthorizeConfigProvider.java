package com.coolron.security;

import com.coolron.security.core.authorize.AuthorizeConfigProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2018/11/10.
 * 实现公共的core 中的权限提供接口
 * 自定义的权限安全配置
 */
@Component
public class DemoAuthorizeConfigProvider implements AuthorizeConfigProvider {

    @Override
    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry conf) {
        // 自定义的权限
        //conf.antMatchers("/demo.html")  // 控制页面
        conf.antMatchers("/user")
                .hasRole("ADMIN");  // 管理员权限

    }
}
