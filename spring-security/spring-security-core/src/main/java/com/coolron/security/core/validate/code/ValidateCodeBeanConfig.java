package com.coolron.security.core.validate.code;

import com.coolron.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ValidateCodeBeanConfig配置如果用户自定义了自己的验证码的生成逻辑，内置的配置就不生效
 * @author zhailiang
 *
 */
@Configuration
public class ValidateCodeBeanConfig {

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 配置默认的图形验证码生成器
     * 此配置 跟 在 ImageCodeGenerator 上加@Component 效果一样
     *
     * 项目可以自己实现验证码的具体实现，但是名字必须是imageCodeGenerator。
     * 如果项目中没有实现，name就使用安全模块中自己实现的验证码逻辑
     * @return
     */
    @Bean
    // 系统启动的时候会到spring 容器中去找 imageCodeGenerator  没找到就执行下面代码
    @ConditionalOnMissingBean(name = "imageCodeGenerator")
    public ValidateCodeGenerator imageCodeGenerator() {
        ImageCodeGenerator codeGenerator = new ImageCodeGenerator();
        codeGenerator.setSecurityProperties(securityProperties);
        return codeGenerator;
    }

    /**
     * 如果项目中配置SmsCodeSender接口的实现，就不会使用下面配置的实现方式
     * @return
     */
    @Bean
    // 系统启动的时候会到spring 容器中去找 imageCodeGenerator  没找到就执行下面代码
    //@ConditionalOnMissingBean(name = "smsCodeSender")
    // 系统找到了这个接口的实现就不会再执行下面的代码了
    @ConditionalOnMissingBean(SmsCodeSender.class)
    public SmsCodeSender smsCodeSender() {
        return new DefaultSmsCodeSender();
    }

}
