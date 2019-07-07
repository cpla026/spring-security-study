package com.coolron.security.core.social.weixin.api;

/**
 * @Auther: xf
 * @Date: 2018/10/31 14:42
 * @Description:
 */
public class WeixinUserInfo {

    private String openId;
    private String nickname;
    private String language;
    /**
     * 1 男 2 女
     */
    private String sex;
    /**
     * 省份
     */
    private String province;
    private String city;
    /**
     * 	国家 CN
     */
    private String country;
    /**
     * 	用户图像 最后一个值代表正方形图像的大小。
     */
    private String headimgurl;

    /**
     * 用户特权信息, json 数组 如微信沃卡用户
     */
    private String[] privilege;
    /**
     * 用户统一标识, 针对一个微信开放平台账号下的应用, 同一用户的unionid是唯一的
     */
    private String unionid;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String[] getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String[] privilege) {
        this.privilege = privilege;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }
}
