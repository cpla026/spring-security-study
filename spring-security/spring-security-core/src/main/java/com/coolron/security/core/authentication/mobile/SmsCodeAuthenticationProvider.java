package com.coolron.security.core.authentication.mobile;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * SmsCodeAuthenticationProvider
 * @Auther: xf
 * @Date: 2018/10/25 09:53
 * @Description:
 */
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;
    /**
     * 身份认证的逻辑
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        // 传进来的认证信息
        SmsCodeAuthenticationToken smsCodeAuthenticationToken = (SmsCodeAuthenticationToken)authentication;
        // getPrincipal() 为 认证之前的登录信息  即 手机号
        UserDetails user = userDetailsService.loadUserByUsername((String) smsCodeAuthenticationToken.getPrincipal());
        if(user == null){
            throw new InternalAuthenticationServiceException("无法获取用户信息");
        }
        /**
         * 读到了用户信息 需要重新封装Token
         * param1: 用户认证信息
         * param2：用户权限
         */
        SmsCodeAuthenticationToken authenticationResult = new SmsCodeAuthenticationToken(user, user.getAuthorities());

        //将之前未认证的请求放进认证后的Token中
        authenticationResult.setDetails(smsCodeAuthenticationToken);

        return authenticationResult;
    }

    /**
     * AuthenticationManager带着Token调用Provider
     * 判断传进来的Token最终调用的是哪个Provider
     */
    @Override
    public boolean supports(Class<?> authentication) {
        // 判断传进来的 authentication 是不是 SmsCodeAuthenticationToken 类型的
        return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}

