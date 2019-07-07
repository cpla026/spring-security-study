package com.coolron.security.browser.validate.code.impl;

import com.coolron.security.core.chonggou.ValidateCodeType;
import com.coolron.security.core.validate.code.ValidateCode;
import com.coolron.security.core.validate.code2.ValidateCodeRepository;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 将验证码存放 session 中的类
 * @author zhailiang
 *
 */
@Component
public class SessionValidateCodeRepository implements ValidateCodeRepository {

    /**
     * 验证码放入session时的前缀
     */
    String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE_";

    /**
     * 操作session的工具类
     */
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    /**
     * redis保存验证码
     */
    @Override
    public void save(ServletWebRequest request, ValidateCode code, ValidateCodeType type) {
        sessionStrategy.setAttribute(request, getSessionKey(request,type), code);
    }

    /**
     * 获取session中的验证码
     */
    @Override
    public ValidateCode get(ServletWebRequest request, ValidateCodeType validateCodeType) {
        return (ValidateCode)sessionStrategy.getAttribute(request,getSessionKey(request, validateCodeType));
    }


    /**
     * 删除session中的验证码
     */
    @Override
    public void remove(ServletWebRequest request, ValidateCodeType type) {
        sessionStrategy.removeAttribute(request, getSessionKey(request, type));
    }

    /**
     * 构建验证码放入session时的key
     * @param request
     * @return
     */
    private String getSessionKey(ServletWebRequest request, ValidateCodeType validateCodeType) {
        return SESSION_KEY_PREFIX + validateCodeType.toString().toUpperCase();
    }

    /**
     * 根据请求的url获取校验码的类型
     *
     * @param request
     * @return
     */
//    private ValidateCodeType getValidateCodeType(ServletWebRequest request) {
//        String simpleName = getClass().getSimpleName();
//        String type = StringUtils.substringBefore(simpleName, "ValidateCodeProcessor");
//        return ValidateCodeType.valueOf(type.toUpperCase());
//    }
}
