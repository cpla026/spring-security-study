package com.coolron.security.browser.logout;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.coolron.security.core.support.SimpleResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 默认的退出成功处理器
 * @Auther: xf
 * @Date: 2018/11/9 10:13
 * @Description:   实现 LogoutSuccessHandler用来处理退出的逻辑处理，是返回json还是根据用户的配置返回Html
 *
 * 在 BrowserSecurityBeanConfig 中注入
 */
@Slf4j
public class CoolronLogoutSuccessHandler implements LogoutSuccessHandler {

    /**
     * 退出成功的 URL 通过外部传进来
     * @param signOutSuccessUrl
     */
    public CoolronLogoutSuccessHandler(String signOutSuccessUrl) {
        this.signOutSuccessUrl = signOutSuccessUrl;
    }

    private String signOutSuccessUrl;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        log.info("退出成功");
        //退出登录的Url如果是默认的空，那么返回的是json数据
        if (StringUtils.isBlank(signOutSuccessUrl)) {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse("退出成功")));
        } else {
            response.sendRedirect(signOutSuccessUrl);
        }

    }

}

