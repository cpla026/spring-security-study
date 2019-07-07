package com.coolron.security.core.validate.code;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by Administrator on 2018/10/21.
 * 短信验证码
 */
public class ValidateCode implements Serializable {

    private static final long serialVersionUID = 2269631737175745957L;
    private String code;
    private LocalDateTime expireTime;  // 过期时间

    public ValidateCode(String code, int expireIn){
        this.code = code;
        // 当前时间 加上 传进来的时间(过期时间)  即得到的是过期的时间点
        this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
    }
    public ValidateCode(String code, LocalDateTime expireTime){
        this.code = code;
        this.expireTime = expireTime;
    }

    // 判断验证码是否过期
    public Boolean isExpired(){
        return LocalDateTime.now().isAfter(expireTime);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }
}
