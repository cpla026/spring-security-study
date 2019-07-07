package com.coolron.security.core.social.qq.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import java.io.IOException;

/**
 * 获取用户信息接口的实现
 * 继承
 * AbstractOAuth2ApiBinding中accessToken是存放前面5步获取的用户信息的
 * 每个用户的用户信息都不相同，因此QQImpl是个多实例的
 * 因此不能将此类申明成为Spring的一个组件。
 * 需要的时候new就可以了。
 *
 * 需要的参数：
 * appId：注册qq互联分配的appid
 * openId：qq用户的Id
 * accessToken：父类提供
 * @author zhailiang
 *
 */
@Slf4j
public class QQImpl extends AbstractOAuth2ApiBinding implements QQ {

    // 获取 openId 的URL
    private static final String URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";

    // 获取用户信息的URL
    private static final String URL_GET_USERINFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";

    private String appId;//注册qq互联分配的appid

    private String openId;//qq用户的Id

    private ObjectMapper objectMapper = new ObjectMapper();

    public QQImpl(String accessToken, String appId) {
        //调用父类构造方法时，将accessToken作为查询参数
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);

        this.appId = appId;

        String url = String.format(URL_GET_OPENID, accessToken);

        //getRestTemplate父类提供  发送请求
        String result = getRestTemplate().getForObject(url, String.class);
        log.info(result);
        // 从结果中获取 openId
        this.openId = StringUtils.substringBetween(result, "\"openid\":\"", "\"}");
    }

    @Override
    public QQUserInfo getUserInfo() {

        String url = String.format(URL_GET_USERINFO, appId, openId);
        String result = getRestTemplate().getForObject(url, String.class);

        log.info(result);

        QQUserInfo userInfo = null;
        try {
            // 将JSonstring 读成 bean
            userInfo = objectMapper.readValue(result, QQUserInfo.class);
            userInfo.setOpenId(openId);
            return userInfo;
        } catch (IOException e) {
            throw new RuntimeException("获取用户信息失败",e);

        }
    }

}

