//package com.coolron.security.browser;
//
//import com.coolron.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
//import com.coolron.security.core.authentication.mobile.SmsCodeFilter;
//import com.coolron.security.core.properties.SecurityProperties;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.authentication.AuthenticationFailureHandler;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
//import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
//
//import javax.sql.DataSource;
//
///**
// * @Auther: xf
// * @Date: 2018/10/24 16:08
// * @Description:
// */
//@Configuration
//public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    // 配置文件读取器
//    @Autowired
//    private SecurityProperties securityProperties;
//
//    // 自定义的登录成功的处理器
//    @Autowired
//    private AuthenticationSuccessHandler coolronAuthenticationSuccessHandler;
//
//    // 自定义登录认证失败的处理器
//    @Autowired
//    private AuthenticationFailureHandler coolronAuthenticationFailureHandler;
//
//    // 数据源
//    @Autowired
//    private DataSource dataSource;
//
//    @Autowired
//    private UserDetailsService userDetailsService;
//
//    // 短信验证码的配置
//    @Autowired
//    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        // 此处可以返回自定义的加密方法(比如MD5 加密)  只需要实现 PasswordEncoder  接口即可
//        // return new StandardPasswordEncoder();
//        return new BCryptPasswordEncoder();
//    }
//
//    /**
//     * 记住我实现
//     * 登陆成功后会将信息存储到数据库中 再次登录有效期内会实现记住功能  即使服务器重启
//     * @return
//     */
//    @Bean
//    public PersistentTokenRepository persistentTokenRepository(){
//        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
//        tokenRepository.setDataSource(dataSource);
//        // 系统启动的时候创建表  也可在 JdbcTokenRepositoryImpl 中复制建表语句
//        //tokenRepository.setCreateTableOnStartup(true);
//        return tokenRepository;
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//
//        ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
//        validateCodeFilter.setAuthenticationFailureHandler(coolronAuthenticationFailureHandler);
//        validateCodeFilter.setSecurityProperties(securityProperties);
//        validateCodeFilter.afterPropertiesSet();
//
//        //短信验证码的配置
//        SmsCodeFilter smsCodeFilter = new SmsCodeFilter();
//        smsCodeFilter.setAuthenticationFailureHandler(coolronAuthenticationFailureHandler);
//        smsCodeFilter.setSecurityProperties(securityProperties);
//        smsCodeFilter.afterPropertiesSet();
//
//        //将验证码的过滤器放在登录验证过滤器之前
//        http.addFilterBefore(smsCodeFilter, UsernamePasswordAuthenticationFilter.class)
//                .addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
//                .formLogin()
//                .loginPage("/authentication/require")
//                .loginProcessingUrl("/authentication/form")
//                .successHandler(coolronAuthenticationSuccessHandler)//登录成功的处理  覆盖系统默认继续执行之前的操作
//                .failureHandler(coolronAuthenticationFailureHandler)//登录失败的处理
//                .and()
//                .rememberMe()  // 记住用户名
//                .tokenRepository(persistentTokenRepository()) // 上面定义的方法
//                .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds()) // 过期时间
//                .userDetailsService(userDetailsService)  // 最后使用它登录
//                .and()
//                .authorizeRequests()
//                .antMatchers("/authentication/require",
//                        securityProperties.getBrowser().getLoginPage(),
//                        "/code/*").permitAll()  // 不需要认证
//                .anyRequest()
//                .authenticated()
//                .and()
//                .csrf().disable()
//                //将前面创建的配置类添加在安全配置类后面
//                .apply(smsCodeAuthenticationSecurityConfig);
//
//    }
//
//}
//
