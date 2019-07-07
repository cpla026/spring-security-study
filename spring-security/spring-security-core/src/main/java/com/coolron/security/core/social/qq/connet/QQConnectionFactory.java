package com.coolron.security.core.social.qq.connet;

import com.coolron.security.core.social.qq.api.QQ;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;

/**
 * 将之前写的
 * QQServiceProvider和QQAdapter传递进来创建
 * QQConnectionFactory
 * providerId：用户在服务商的唯一标示openId
 * @author zhailiang
 *
 */
public class QQConnectionFactory extends OAuth2ConnectionFactory<QQ> {

    public QQConnectionFactory(String providerId, String appId, String appSecret) {
        super(providerId, new QQServiceProvider(appId, appSecret), new QQAdapter());
    }

}

