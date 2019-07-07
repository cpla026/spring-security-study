package com.coolron.security.app.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.coolron.security.core.properties.LoginType;
import com.coolron.security.core.properties.SecurityProperties;
import com.coolron.security.core.support.SimpleResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2018/10/21.
 * 登录失败的处理器
 */
@Slf4j
@Component("coolronAuthenticationFailureHandler")
//public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
// spring security 默认的登录失败的处理器
public class CoolronAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * @param request
     * @param response
     * @param exception  // 认证过程中发生错误产生的异常信息
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        log.info("登录失败");

        // 如果配置的是 JSON   在配置文件中配置
        if(LoginType.JSON.equals(securityProperties.getBrowser().getLoginType())){
            // 将错误息封装成 json 返回
            // 500
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse(exception.getMessage())));
        } else{
            // 父类跳转
            super.onAuthenticationFailure(request, response, exception);
        }
    }
}
