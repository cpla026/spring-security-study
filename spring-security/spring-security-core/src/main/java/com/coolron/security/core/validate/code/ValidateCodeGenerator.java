package com.coolron.security.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * @Auther: xf
 * @Date: 2018/10/22 13:48
 * @Description: 验证码生成器
 *
 * 声明成接口目的: 使验证码生成逻辑是可配置的    其他用户只需实现此接口覆盖之前逻辑即可
 */
public interface ValidateCodeGenerator {
    ValidateCode  generate(ServletWebRequest request);
}
