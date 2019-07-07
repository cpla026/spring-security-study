package com.coolron.security.core.properties;

/**
 * Created by Administrator on 2018/10/21.
 */
public class BrowserProperties {

    // 默认注册页
    private String singUpUrl = "/coolron-signUp.html";

    //退出的url,默认为空   此处没有指定默认值,若配置文件中没有配置  会报错 Caused by: java.lang.IllegalArgumentException: Pattern cannot be null or empty
    private String signOutUrl;

    // 如用户没有配置loginPage  则默认是 /demo-signIn.html
    private String loginPage = "/coolron-signIn.html";

    // 默认返回 JSON
    private LoginType loginType = LoginType.JSON;

    // 记住我的时间
    private int rememberMeSeconds = 3600;

    // session
    private SessionProperties session = new SessionProperties();

    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }

    public LoginType getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginType loginType) {
        this.loginType = loginType;
    }

    public int getRememberMeSeconds() {
        return rememberMeSeconds;
    }

    public void setRememberMeSeconds(int rememberMeSeconds) {
        this.rememberMeSeconds = rememberMeSeconds;
    }

    public String getSingUpUrl() {
        return singUpUrl;
    }

    public void setSingUpUrl(String singUpUrl) {
        this.singUpUrl = singUpUrl;
    }

    public SessionProperties getSession() {
        return session;
    }

    public void setSession(SessionProperties session) {
        this.session = session;
    }

    public String getSignOutUrl() {
        return signOutUrl;
    }

    public void setSignOutUrl(String signOutUrl) {
        this.signOutUrl = signOutUrl;
    }
}
