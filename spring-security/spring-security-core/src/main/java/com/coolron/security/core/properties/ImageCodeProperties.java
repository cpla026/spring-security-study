package com.coolron.security.core.properties;

/**
 * @Auther: xf
 * @Date: 2018/10/22 10:30
 * @Description:  图片验证码配置
 */
public class ImageCodeProperties extends SmsCodeProperties {

    // 默认级的配置
    private int width = 67;
    private int height = 23;

    /**
     * 由于继承 SmsCodeProperties  length 默认为6
     * 此处修改 : new ImageCodeProperties() 的时候设置 length 为 6
     */
    public ImageCodeProperties(){
        setLength(4);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

}
