package com.coolron.security.core.social.weixin.api;

/**
 * @Auther: xf
 * @Date: 2018/10/31 14:38
 * @Description:  微信API调用接口
 */
public interface Weixin {
    WeixinUserInfo getUserInfo(String openId);
}

