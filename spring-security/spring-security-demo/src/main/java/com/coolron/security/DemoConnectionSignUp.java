package com.coolron.security;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Component;

/**
 * 还有一种情况，就是将QQ的用户信息默认注册为一个在第三方中的用户：
 * 如若配置了DemoConnectionSignUp，就会默认将qq用户注册为一个新用户，不用跳转到注册或绑定的页面
 * @author zhailiang
 *
 */
@Component
public class DemoConnectionSignUp implements ConnectionSignUp {

    /* (non-Javadoc)
     * @see org.springframework.social.connect.ConnectionSignUp#execute(org.springframework.social.connect.Connection)
     */
    @Override
    public String execute(Connection<?> connection) {
        //根据社交用户信息默认创建用户并返回用户唯一标识
        return connection.getDisplayName();
    }

}
