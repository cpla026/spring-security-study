package com.coolron.security.core.chonggou;

import com.coolron.security.core.validate.code.ValidateCodeException;
import com.coolron.security.core.validate.code2.ValidateCodeProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Auther: xf
 * @Date: 2018/10/25 10:34
 * @Description: 依赖查找出相应的验证码处理器
 */
@Component
public class ValidateCodeProcessorHolder {

    /**
     * 依赖查找
     */
    @Autowired
    private Map<String, ValidateCodeProcessor> validateCodeProcessors;

    public ValidateCodeProcessor findValidateCodeProcessor(ValidateCodeType type) {
        return findValidateCodeProcessor(type.toString().toLowerCase());
    }

    public ValidateCodeProcessor findValidateCodeProcessor(String type) {
        String name = type.toLowerCase() + ValidateCodeProcessor.class.getSimpleName();
        ValidateCodeProcessor processor = validateCodeProcessors.get(name);
        if (processor == null) {
            throw new ValidateCodeException("验证码处理器" + name + "不存在");
        }
        return processor;
    }

}

