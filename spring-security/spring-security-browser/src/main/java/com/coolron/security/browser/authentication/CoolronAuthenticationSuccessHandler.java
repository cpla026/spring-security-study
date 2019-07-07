package com.coolron.security.browser.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.coolron.security.core.properties.LoginType;
import com.coolron.security.core.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2018/10/21.
 * 自定义登录成功的处理
 * 默认登陆成功是继续执行之前的请求
 */
@Slf4j
@Component("coolronAuthenticationSuccessHandler")
//public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
// SavedRequestAwareAuthenticationSuccessHandler security 默认的处理器
public class CoolronAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SecurityProperties securityProperties;

    /**
     *
     * @param request
     * @param response
     * @param authentication  // 封装认证信息 例如: 微信登录 会包含openId 等
     * @throws IOException
     * @throws ServletException
     *
     * 登录成功之后会调用的方法
     *
     * 之后再去调用其他请求 会直接请求URL 不经过此控制器
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        log.info("登录成功");

        // 如果配置的是 JSON   在配置文件中配置
        if(LoginType.JSON.equals(securityProperties.getBrowser().getLoginType())){
            // 将认证信息封装成 json 返回
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(authentication));
        } else {
            //父类方法，springboot默认的处理方式
            super.onAuthenticationSuccess(request,response,authentication);
        }

    }

}
