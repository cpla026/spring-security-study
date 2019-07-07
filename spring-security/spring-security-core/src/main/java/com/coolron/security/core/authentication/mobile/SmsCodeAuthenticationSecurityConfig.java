package com.coolron.security.core.authentication.mobile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 *
 * 将前面的三个类穿在一起
 * 在浏览器也要在app中使用
 *
 * @Auther: xf
 * @Date: 2018/10/22 18:31
 * @Description:  短信验证码的配置
 */
@Component
public class SmsCodeAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    // 登录成功的处理器
    @Autowired
    private AuthenticationSuccessHandler coolronAuthenticationSuccessHandler;

    // 登录失败的处理器
    @Autowired
    private AuthenticationFailureHandler coolronAuthenticationFailureHandler;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // 配置过滤器
        SmsCodeAuthenticationFilter smsCodeAuthenticationFilter = new SmsCodeAuthenticationFilter();
        smsCodeAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        // 成功处理器
        smsCodeAuthenticationFilter.setAuthenticationSuccessHandler(coolronAuthenticationSuccessHandler);
        // 失败处理器
        smsCodeAuthenticationFilter.setAuthenticationFailureHandler(coolronAuthenticationFailureHandler);

        //
        SmsCodeAuthenticationProvider smsCodeAuthenticationProvider = new SmsCodeAuthenticationProvider();
        // 设置 userDetailsService 用于读取用户信息
        smsCodeAuthenticationProvider.setUserDetailsService(userDetailsService);

        // 将以上两个组件加到 spring security 安全框架中
        //smsCodeAuthenticationProvider设置在AuthenticationManager管理的集合上面
        http.authenticationProvider(smsCodeAuthenticationProvider)
                // 将 smsCodeAuthenticationFilter 加到 UsernamePasswordAuthenticationFilter 后面去
                .addFilterAfter(smsCodeAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    }
}
