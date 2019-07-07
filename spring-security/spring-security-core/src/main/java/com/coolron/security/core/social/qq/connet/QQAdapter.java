package com.coolron.security.core.social.qq.connet;

import com.coolron.security.core.social.qq.api.QQ;
import com.coolron.security.core.social.qq.api.QQUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * QQAdapter
 * 将服务提供商用户信息进行统一的适配
 * @author zhailiang
 *
 */
public class QQAdapter implements ApiAdapter<QQ> {

    @Override
    public boolean test(QQ api) {
        return true;
    }

    //设置创建Connection的时候需要的一些配置项ConnectionValues
    @Override
    public void setConnectionValues(QQ api, ConnectionValues values){
        QQUserInfo userInfo = api.getUserInfo();

        values.setDisplayName(userInfo.getNickname());
        values.setImageUrl(userInfo.getFigureurl_qq_1());
        values.setProfileUrl(null);//主页
        values.setProviderUserId(userInfo.getOpenId());//用户在服务提供商的唯一标示，openID
    }

    //绑定解绑的时候
    @Override
    public UserProfile fetchUserProfile(QQ api) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void updateStatus(QQ api, String message) {
        //do noting
    }

}

