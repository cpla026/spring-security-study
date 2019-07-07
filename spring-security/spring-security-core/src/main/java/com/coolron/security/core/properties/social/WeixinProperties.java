package com.coolron.security.core.properties.social;

import org.springframework.boot.autoconfigure.social.SocialProperties;

/**
 * @Auther: xf
 * @Date: 2018/10/31 14:28
 * @Description:
 */
public class WeixinProperties extends SocialProperties {

    /**
     * 第三方 id, 用来决定发起第三方登录的URL, 默认是 weixin
     */
    private String providerId = "weixin";

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }
}
