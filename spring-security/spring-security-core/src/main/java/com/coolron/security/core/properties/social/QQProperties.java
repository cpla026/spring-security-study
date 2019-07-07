package com.coolron.security.core.properties.social;

import org.springframework.boot.autoconfigure.social.SocialProperties;

/**
 * QQProperties
 * @author zhailiang
 * SocialProperties 中封装了 appId 和 appSecret
 */
public class QQProperties extends SocialProperties {

    // 服务提供商的标识
    private String providerId = "qq";//默认的providerId

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }
}

