package com.coolron.security.core.social.qq.connet;

import com.coolron.security.core.social.qq.api.QQ;
import com.coolron.security.core.social.qq.api.QQImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

/**
 * QQServiceProvider
 * appId：注册qq互联分配的id
 * appSecret：注册qq互联的分配密码
 * @author zhailiang
 *
 */
public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQ> {

    private String appId;

    //将用户导向的认证服务器的地址
    private static final String URL_AUTHORIZE = "https://graph.qq.com/oauth2.0/authorize";
    //第三方拿着授权码获取Token的地址
    private static final String URL_ACCESS_TOKEN = "https://graph.qq.com/oauth2.0/token";

    //提供OAuth2Operations
    public QQServiceProvider(String appId, String appSecret) {
        super(new QQOAuth2Template(appId, appSecret, URL_AUTHORIZE, URL_ACCESS_TOKEN));
        this.appId = appId;
    }

    //提供Api
    @Override
    public QQ getApi(String accessToken) {
        return new QQImpl(accessToken, appId);
    }

}

