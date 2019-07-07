package com.coolron.security.browser;

/**
 * 跳转到用户注册绑定页面携带的qq用户信息
 * @author zhailiang
 *
 */
public class SocialUserInfo {

    private String providerId;

    private String providerUserId;  // openId

    private String nickname;  // 昵称

    private String headimg;   // 用户图像

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getProviderUserId() {
        return providerUserId;
    }

    public void setProviderUserId(String providerUserId) {
        this.providerUserId = providerUserId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

}

