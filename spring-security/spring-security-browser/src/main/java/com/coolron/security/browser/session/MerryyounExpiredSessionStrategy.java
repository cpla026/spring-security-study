package com.coolron.security.browser.session;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @Auther: xf
 * @Date: 2018/11/8 14:11
 * @Description:
 */
@Slf4j
public class MerryyounExpiredSessionStrategy implements SessionInformationExpiredStrategy {

    /**
     * @param eventØ  session超时的事件
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent eventØ) throws IOException, ServletException {
        eventØ.getResponse().setContentType("application/json;charset=UTF-8");
        eventØ.getResponse().getWriter().write("并发登录!");
    }
}

