package com.coolron.security.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * @Auther: xf
 * @Date: 2018/10/22 14:24
 * @Description:   自定义图形验证码生成器   可覆盖默认的图形验证码生成器
 */
// 结合 ValidateCodeBeanConfig 看  有相同的 不会再在ValidateCodeBeanConfig 配置中创建 imageCodeGenerator 即实现了覆盖的效果
// 系统启动后在spring容器中会有 imageCodeGenerator
//@Component("imageCodeGenerator")
public class DemoImageCodeGenerator implements ValidateCodeGenerator {

    /**
     * 测试自定义的图形验证码  会覆盖默认的
     *
     *  此处返回后会报 空指针异常
     *
     * @param request
     * @return
     */
    @Override
    public ImageCode generate(ServletWebRequest request) {

        System.out.println("自定义的图形验证码生成器");
        return null;
    }
}
