package com.coolron.security.browser.chonggou;

import com.coolron.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.coolron.security.core.authorize.AuthorizeConfigManager;
import com.coolron.security.core.chonggou.AbstractChannelSecurityConfig;
import com.coolron.security.core.chonggou.ValidateCodeSecurityConfig;
import com.coolron.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

/** 安全配置类
 * @Auther: xf
 * @Date: 2018/10/25 10:39
 * @Description:  重构
 */
@Configuration
public class BrowserSecurityConfig extends AbstractChannelSecurityConfig {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserDetailsService userDetailsService;

    // 短信登录的配置
    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    // 校验码相关的配置
    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

    @Autowired
    private SpringSocialConfigurer coolronSocialSecurityConfig;

    // 并发登录的自定义处理
    @Autowired
    private InvalidSessionStrategy invalidSessionStrategy;

    // 失效时的跳转
    @Autowired
    private SessionInformationExpiredStrategy expiredSessionStrategy;

    // 退出成功处理器
    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;

    // 权限公共的配置管理器
    @Autowired
    private AuthorizeConfigManager coolronAuthorizeConfigManager;


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 密码相关的配置
        applyPasswordAuthenticationConfig(http);

        http.apply(validateCodeSecurityConfig)
                .and()
                .apply(smsCodeAuthenticationSecurityConfig)
                .and()
                .apply(coolronSocialSecurityConfig) // 将SpringSocialFilter 添加到过滤器链上
                .and()
            .rememberMe()
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
                .userDetailsService(userDetailsService)
                .and()
            .sessionManagement()    // session 管理
                //.invalidSessionUrl(SecurityConstants.DEFAULT_SESSION_INVALID_URL)  // 过期的 URL
                .invalidSessionStrategy(invalidSessionStrategy)
                .maximumSessions(securityProperties.getBrowser().getSession().getMaximumSessions()) // 最大session并发数量1 后面的登录可以挤掉前面的session
                .maxSessionsPreventsLogin(securityProperties.getBrowser().getSession().isMaxSessionsPreventsLogin())//false之后登录踢掉之前登录,true则不允许之后登录
                //.expiredSessionStrategy(new MerryyounExpiredSessionStrategy())//登录被踢掉时的自定义操作
                .expiredSessionStrategy(expiredSessionStrategy)
                .and()
                .and()
            .logout()
                .logoutUrl("/signOut")
                .logoutSuccessHandler(logoutSuccessHandler)//退出成功后自定义的处理逻辑
        //		.logoutSuccessUrl("退出成功跳转的Url")//两者配置一个就可以
                .deleteCookies("JSESSIONID")//同时删除Cookie
                .and()
           /* .authorizeRequests()
                .antMatchers(
                        SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
                        SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,
                        securityProperties.getBrowser().getLoginPage(),
                        SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX+"*//*",
                        securityProperties.getBrowser().getSingUpUrl(),
                        "/user/regist",
                        securityProperties.getBrowser().getSession().getSessionInvalidUrl(),
                        securityProperties.getBrowser().getSignOutUrl()) // 退出的页面
                        .permitAll()
                .anyRequest()
                .authenticated()
                .and()*/
                .csrf().disable();
        // 替换上面注释掉的配置
        coolronAuthorizeConfigManager.config(http.authorizeRequests());

    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
//		tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }

}

