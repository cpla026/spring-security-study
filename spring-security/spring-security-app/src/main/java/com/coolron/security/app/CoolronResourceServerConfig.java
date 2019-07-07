package com.coolron.security.app;

import com.coolron.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.coolron.security.core.authorize.AuthorizeConfigManager;
import com.coolron.security.core.chonggou.SecurityConstants;
import com.coolron.security.core.chonggou.ValidateCodeSecurityConfig;
import com.coolron.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * Created by Administrator on 2018/11/6.
 * 服务提供商 --- 资源服务器
 * 添加访问资源的安全配置 类似 browser项目的 BrowserSecurityConfig
 *
 * 测试:
 * 用PostMan做一个登陆的请求，携带用户名和密码并携带：authentication（封装了clientId和clientSecret）的信息
 * 因为项目的用户名密码的登录请求路径是authentication/form
 * localhost:80/authentication/form
 * 请求头 :
 * Authorization 指定用户名和密码(配置文件中配置的security.oauth2.client.client-id 和 security.oauth2.client.client-secret)
 * 返回 token信息
 */
@Configuration
@EnableResourceServer
public class CoolronResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    protected AuthenticationSuccessHandler coolronAuthenticationSuccessHandler;

    @Autowired
    protected AuthenticationFailureHandler coolronAuthenticationFailureHandler;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

    @Autowired
    private SpringSocialConfigurer coolronSocialSecurityConfig;

    @Autowired
    private SecurityProperties securityProperties;

    // 权限公共的配置管理器
    @Autowired
    private AuthorizeConfigManager coolronAuthorizeConfigManager;

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.formLogin()
                .loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
                .loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)
                .successHandler(coolronAuthenticationSuccessHandler)
                .failureHandler(coolronAuthenticationFailureHandler);

        http.apply(validateCodeSecurityConfig) // 图片验证码
                .and()
                .apply(smsCodeAuthenticationSecurityConfig) // 短信验证码
                .and()
                .apply(coolronSocialSecurityConfig) // 第三方登录
                .and()
          /*  .authorizeRequests()
                .antMatchers(
                        SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
                        SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,
                        securityProperties.getBrowser().getLoginPage(),
                        SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX+"*//*",
                        securityProperties.getBrowser().getSingUpUrl(),
                        securityProperties.getBrowser().getSession().getSessionInvalidUrl(),
                        securityProperties.getBrowser().getSignOutUrl(),
                        "/user/regist")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()*/
                .csrf().disable();

        // 替换上面注释掉的配置
        coolronAuthorizeConfigManager.config(http.authorizeRequests());
    }

}

