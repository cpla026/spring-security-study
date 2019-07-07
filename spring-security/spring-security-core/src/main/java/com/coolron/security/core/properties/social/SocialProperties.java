package com.coolron.security.core.properties.social;

/**
 * @Auther: xf
 * @Date: 2018/10/25 15:16
 * @Description:
 */
public class SocialProperties {

    private QQProperties qq = new QQProperties();

    private WeixinProperties weixin = new WeixinProperties();

    // 默认
    private String filterProcessesUrl = "/auth";

    public QQProperties getQq() {
        return qq;
    }

    public void setQq(QQProperties qq) {
        this.qq = qq;
    }

    public String getFilterProcessesUrl() {
        return filterProcessesUrl;
    }

    public void setFilterProcessesUrl(String filterProcessesUrl) {
        this.filterProcessesUrl = filterProcessesUrl;
    }

    public WeixinProperties getWeixin() {
        return weixin;
    }

    public void setWeixin(WeixinProperties weixin) {
        this.weixin = weixin;
    }
}

