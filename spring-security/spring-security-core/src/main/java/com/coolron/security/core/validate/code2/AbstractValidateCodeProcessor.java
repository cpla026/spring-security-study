package com.coolron.security.core.validate.code2;

import com.coolron.security.core.chonggou.ValidateCodeType;
import com.coolron.security.core.validate.code.ValidateCode;
import com.coolron.security.core.validate.code.ValidateCodeException;
import com.coolron.security.core.validate.code.ValidateCodeGenerator;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;

public abstract class AbstractValidateCodeProcessor<C extends ValidateCode> implements ValidateCodeProcessor {

    @Autowired
    private ValidateCodeRepository validateCodeRepository;

    /**
     * 依赖查找
     *
     * 短信验证码  和 图片验证码 的生成逻辑  都封装在 ValidateCodeProcessor 接口下面的实现类
     * spring 启动的时候会查找所有这个接口的实现  将相应的bean 作为key 放到 Map 中去
     *
     * 收集系统中所有的 {@link ValidateCodeGenerator} 接口的实现。
     */
    @Autowired
    private Map<String, ValidateCodeGenerator> validateCodeGenerators;


    @Override
    public void create(ServletWebRequest request) throws Exception {
        // 生成
        C validateCode = generate(request);
        // 保存
        save(request, validateCode);
        // 发送  抽象的方法
        send(request, validateCode);
    }

    /**
     * 生成校验码
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    private C generate(ServletWebRequest request) {
        // 请求的后半段  image   sms
        String type = getProcessorType(request);
        ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators.get(type + "ValidateCodeGenerator");
        return (C) validateCodeGenerator.generate(request);
    }

    /**
     * 保存校验码
     * @param request
     * @param validateCode
     */
    private void save(ServletWebRequest request, C validateCode) {
        // session存储策略设置成redis后 需处理存redis 序列化问题
        ValidateCode code = new ValidateCode(validateCode.getCode(), validateCode.getExpireTime());
        //sessionStrategy.setAttribute(request, SESSION_KEY_PREFIX + getProcessorType(request).toUpperCase(), code);
        // 存储到redis中
        validateCodeRepository.save(request, code, getValidateCodeType(request));
        // 原始
        //sessionStrategy.setAttribute(request, SESSION_KEY_PREFIX + getProcessorType(request).toUpperCase(), validateCode);
    }

    /**
     * 发送校验码，由子类实现
     * @param request
     * @param validateCode
     * @throws Exception
     */
    protected abstract void send(ServletWebRequest request, C validateCode) throws Exception;

    /**
     * 根据请求的url获取校验码的类型
     * @param request
     * @return
     */
    private String getProcessorType(ServletWebRequest request) {
        // 请求的后半段
        return StringUtils.substringAfter(request.getRequest().getRequestURI(), "/code/");
    }
    /**
     * 实现父类的验证方法
     */
    @SuppressWarnings("unchecked")
    @Override
    public void validate(ServletWebRequest request) {

        ValidateCodeType processorType = getValidateCodeType(request);
       // String sessionKey = getSessionKey(request);

        //C codeInSession = (C) sessionStrategy.getAttribute(request, sessionKey);
        // 从redis中获取
        C codeInSession = (C) validateCodeRepository.get(request, processorType);

        String codeInRequest;
        try {
            codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(),
                    processorType.getParamNameOnValidate());
        } catch (ServletRequestBindingException e) {
            throw new ValidateCodeException("获取验证码的值失败");
        }

        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException(processorType + "验证码的值不能为空");
        }

        if (codeInSession == null) {
            throw new ValidateCodeException(processorType + "验证码不存在");
        }

        if (codeInSession.isExpired()) {
            //sessionStrategy.removeAttribute(request, sessionKey);
            validateCodeRepository.remove(request, processorType);
            throw new ValidateCodeException(processorType + "验证码已过期");
        }

        if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
            throw new ValidateCodeException(processorType + "验证码不匹配");
        }

        //sessionStrategy.removeAttribute(request, sessionKey);
        validateCodeRepository.remove(request, processorType);
    }

    /**
     * 根据请求的url获取校验码的类型
     *
     * @param request
     * @return
     */
    private ValidateCodeType getValidateCodeType(ServletWebRequest request) {
        String simpleName = getClass().getSimpleName();
        String type = StringUtils.substringBefore(simpleName, "ValidateCodeProcessor");
        return ValidateCodeType.valueOf(type.toUpperCase());
    }

}
