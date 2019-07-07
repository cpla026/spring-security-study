package com.coolron.security.core.validate.code;

import org.springframework.security.core.AuthenticationException;

/**
 * Created by Administrator on 2018/10/21.
 * 图形验证码的异常
 * AuthenticationException : spring security 中所有异常的基类
 */

public class ValidateCodeException extends AuthenticationException {

    public ValidateCodeException(String msg) {
        super(msg);
    }
}
