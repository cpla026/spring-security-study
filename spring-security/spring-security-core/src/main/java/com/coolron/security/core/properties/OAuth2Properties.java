package com.coolron.security.core.properties;

/**
 * @Auther: xf
 * @Date: 2018/11/9 17:43
 * @Description:
 */
public class OAuth2Properties {

    private OAuth2ClientProperties[] clients = {};  // 默认空数组

    private String jwtSigningKey = "coolron";     // 密签

    public OAuth2ClientProperties[] getClients() {
        return clients;
    }

    public void setClients(OAuth2ClientProperties[] clients) {
        this.clients = clients;
    }

    public String getJwtSigningKey() {
        return jwtSigningKey;
    }

    public void setJwtSigningKey(String jwtSigningKey) {
        this.jwtSigningKey = jwtSigningKey;
    }
}

