package com.coolron.security.core.social;

import com.coolron.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

/**
 * 操作UserConnetion表的配置类
 * 配置getUsersConnectionRepository操作Connetion数据库的表
 * 在这里面可以定义创建表的前缀信息和存入数据库数据的加解密的方法
 * JdbcUsersConnectionRepository.sql中有建表的语句：
 * 其中重要的一些字段：
 * userId：业务系统Id
 * providerId:服务提供商的Id
 * providerUserId:用户在服务提供商的Id
 *
 * @author zhailiang
 *
 */
@Order(1)
@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private SecurityProperties securityProperties;

    // 根据用户是否配置来注入  参看 DemoConnectionSignUp
    @Autowired(required = false)
    private ConnectionSignUp connectionSignUp;

//    @Autowired
//    private ConnectionFactoryLocator connectionFactoryLocator;

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        /**
         * Encryptors.noOpText()不做加解密
         * 建表语句 在  JdbcUsersConnectionRepository 依赖的 .sql中
         */
        JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
        //建表的前缀  若有 需要加  默认是 UserConnection
        // repository.setTablePrefix("t_");

        if(connectionSignUp != null){
            repository.setConnectionSignUp(connectionSignUp);
        }
        return repository;
    }

    //将SpringSocialFilter添加到安全配置的Bean
    @Bean
    public SpringSocialConfigurer coolronSpringSocialConfigurer() {
        String filterProcessesUrl = securityProperties.getSocial().getFilterProcessesUrl();
        // 通过此构造函数向 CoolronSpringSocialConfigurer 设置 filterProcessesUrl
        CoolronSpringSocialConfigurer coolronSpringSocialConfigurer = new CoolronSpringSocialConfigurer(filterProcessesUrl);
        // 自己的注册页面
        coolronSpringSocialConfigurer.signupUrl(securityProperties.getBrowser().getSingUpUrl());
        return coolronSpringSocialConfigurer;
    }

    /**
     * 工具类
     * 跳转到注册页面时携带扣扣信息,
     * 并且在注册成功时将用户的唯一标识放到social中 一起存到数据库中
     * @return
     */
    @Bean
    public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator){
        return new ProviderSignInUtils(connectionFactoryLocator,getUsersConnectionRepository(connectionFactoryLocator));
    }
}

