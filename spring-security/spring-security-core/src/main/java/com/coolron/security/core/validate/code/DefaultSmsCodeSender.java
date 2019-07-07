package com.coolron.security.core.validate.code;

/**
 * @Auther: xf
 * @Date: 2018/10/22 16:08
 * @Description:  默认的短信验证码发送服务
 */

public class DefaultSmsCodeSender implements SmsCodeSender {

    @Override
    public void send(String mobile, String code) {
        System.out.println("向手机 : "+ mobile +"发送短信验证码 : " + code);
    }
}
