package com.coolron.security.core.validate.code;

/**
 *
 * 短信发送的接口
 *
 * @Auther: xf
 * @Date: 2018/10/22 16:05
 * @Description:  不同的人使用的不同的短信服务商   所有提供  短信验证码发送接口
 */
public interface SmsCodeSender {

    void send(String mobile, String code);
}
