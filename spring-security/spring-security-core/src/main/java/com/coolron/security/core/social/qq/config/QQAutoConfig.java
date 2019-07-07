package com.coolron.security.core.social.qq.config;

import com.coolron.security.core.properties.SecurityProperties;
import com.coolron.security.core.properties.social.QQProperties;
import com.coolron.security.core.social.qq.connet.QQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;

/**
 * QQAutoConfig配置类
 * @author zhailiang
 *  系统启动的时候    自动装配到  QQConnectionFactory QQ连接工厂 中
 */
@Configuration
// 作用 : 当配置了  coolron.security.social.qq.app-id 时才生效
@ConditionalOnProperty(prefix = "coolron.security.social.qq", name = "app-id")
public class QQAutoConfig extends SocialAutoConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;


    //将配置文件中的ProviderId，AppId，AppSecret读取出来，给QQConnectionFactory
    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        QQProperties qqConfig = securityProperties.getSocial().getQq();
        /**
         * param1 : providerId
         * param2 : appId
         * param3 : appSecret
         */
        return new QQConnectionFactory(qqConfig.getProviderId(), qqConfig.getAppId(), qqConfig.getAppSecret());
    }

}

