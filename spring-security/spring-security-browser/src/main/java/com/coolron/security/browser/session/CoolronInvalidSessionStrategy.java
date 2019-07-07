package com.coolron.security.browser.session;

import org.springframework.security.web.session.InvalidSessionStrategy;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 并发登录的自定义处理
 * @author zhailiang
 *
 */
public class CoolronInvalidSessionStrategy extends AbstractSessionStrategy implements InvalidSessionStrategy {

    public CoolronInvalidSessionStrategy(String invalidSessionUrl) {
        super(invalidSessionUrl);
    }

    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        onSessionInvalid(request, response);
    }

}
