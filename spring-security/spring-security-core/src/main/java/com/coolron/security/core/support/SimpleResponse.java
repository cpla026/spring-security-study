package com.coolron.security.core.support;

/**
 * @Auther: xf
 * @Date: 2018/10/23 13:59
 * @Description:  结果处理
 */
public class SimpleResponse {

    public Object content;

    public SimpleResponse(Object content) {
        this.content = content;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}
