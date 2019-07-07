package com.coolron.security.core.properties;

/**
 * @Auther: xf
 * @Date: 2018/10/22 10:30
 * @Description:  短信验证码配置
 */
public class SmsCodeProperties {
    // 默认级的配置
    private int length = 6;
    private int expireIn = 60; // 过期时间
    private String url = "";  // 需要验证码的 URL

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getExpireIn() {
        return expireIn;
    }

    public void setExpireIn(int expireIn) {
        this.expireIn = expireIn;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
