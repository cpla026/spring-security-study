package com.coolron.security.core.properties;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by Administrator on 2018/10/21.
 */
@Configuration
// 作用 : 使SecurityProperties 配置文件读取器生效
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityCoreConfig {

    /**
     * 密码加解密
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // 此处可以返回自定义的加密方法(比如MD5 加密)  只需要实现 PasswordEncoder  接口即可
        return new BCryptPasswordEncoder();
    }
}
