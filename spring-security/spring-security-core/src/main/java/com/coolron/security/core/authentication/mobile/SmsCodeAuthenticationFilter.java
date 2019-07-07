package com.coolron.security.core.authentication.mobile;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 短信登录的过滤器
 * @Auther: xf
 * @Date: 2018/10/25 09:45
 * @Description:  参照UsernamePasswordAuthenticationFilter
 */
public class SmsCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    //请求中携带的参数名
    public static final String SPRING_SECURITY_FORM_SMS_KEY = "mobile";

    // 请求中携带参数的名字是什么
    private String mobileParameter = SPRING_SECURITY_FORM_SMS_KEY;

    //是否仅处理post请求
    private boolean postOnly = true;


    /**
     * Constructors
     * 处理的短信登录的请求是什么
     */
    public SmsCodeAuthenticationFilter() {
        // 请求路径 和 方式
        super(new AntPathRequestMatcher("/authentication/mobile", "POST"));
    }

    /*
     * Methods
     * 认证流程
     */
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        if (postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        String mobile = obtainMobile(request);
        if (mobile == null) {
            mobile = "";
        }
        mobile = mobile.trim();
        // 实例化 Token
        SmsCodeAuthenticationToken authRequest = new SmsCodeAuthenticationToken(mobile);
        // 将请求的信息设置在Token中
        setDetails(request, authRequest);
        //拿着Token调用AuthenticationManager
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    /**
     * 获取请求参数中的手机号
     * @param request
     * @return
     */
    protected String obtainMobile(HttpServletRequest request) {
        return request.getParameter(mobileParameter);
    }

    /**
     * 将请求的信息设置在Token中
     * @param request
     * @param authRequest
     */
    protected void setDetails(HttpServletRequest request, SmsCodeAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    public void setMobileParameter(String mobileParameter) {
        Assert.hasText(mobileParameter, "Mobile parameter must not be empty or null");
        this.mobileParameter = mobileParameter;
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public final String getMobileParameter() {
        return mobileParameter;
    }
}

