package com.coolron.security.core.validate.code;

import com.coolron.security.core.validate.code2.ValidateCodeProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by Administrator on 2018/10/21.
 * 图片检验码
 */
@RestController
public class ValidateCodeController {

    @Autowired
    private Map<String, ValidateCodeProcessor> validateCodeGenerators;

    /**
     * 创建验证码  根据验证码类型的不同, 调用不同的{@link ValidateCodeProcessor} 接口的实现。
     * @param request
     * @param response
     * @param type
     */
    @GetMapping("/code/{type}")
    public void createCode(HttpServletRequest request, HttpServletResponse response, @PathVariable String type) throws Exception {
        validateCodeGenerators.get(type + "ValidateCodeProcessor").create(new ServletWebRequest(request, response));
    }

  /*  public static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    // 图片验证码
    @Autowired
    private ValidateCodeGenerator imageCodeGenerator;

    // 短信验证码
    @Autowired
    private ValidateCodeGenerator smsCodeGenerator;

    // 验证码发送器
    @Autowired
    private SmsCodeSender smsCodeSender;


    @GetMapping("/code/image")
    public void createCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //1：根据随机数生成图片
        ImageCode imageCode = (ImageCode)imageCodeGenerator.generate(new ServletWebRequest(request));
        //2：将图片放入session中
        sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY, imageCode);
        //3：将生成的图片写入接口的响应中
        ImageIO.write(imageCode.getImage(), "JPEG", response.getOutputStream());
    }

    *//**
     * 短信验证码
     * @param request
     * @param response
     * @throws IOException
     *//*
    @GetMapping("/code/sms")
    public void createSmsCode(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletRequestBindingException {
        // 1. 根据随机数生成图片
        ValidateCode smsCode = smsCodeGenerator.generate(new ServletWebRequest(request));
        // 2. 将随机数存储到session中
        // param1: 请求, 传入请求  sessionStrtegy 会从中拿
        // param2: session的key
        // param3: session的value
        sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY , smsCode);
        //String mobile = ServletRequestUtils.getStringParameter(request, "mobile");
        // 必须传的参数
        String mobile = ServletRequestUtils.getRequiredStringParameter(request, "mobile");

        // 发送  调用短信服务商
        smsCodeSender.send(mobile, smsCode.getCode());
    }*/

}
