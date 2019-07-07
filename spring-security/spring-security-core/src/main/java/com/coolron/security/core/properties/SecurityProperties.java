package com.coolron.security.core.properties;

import com.coolron.security.core.properties.social.SocialProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by Administrator on 2018/10/21.
 *
 * 配置文件读取器  读取整个系统配置
 */
// 会读取 coolron.security 开头的配置项
@ConfigurationProperties(prefix = "coolron.security")
@Data
public class SecurityProperties {

    // browser 会读取到 此类中去
    private BrowserProperties browser = new BrowserProperties();

    private ValidateCodeProperties code = new ValidateCodeProperties();

    // 社交
    private SocialProperties social = new SocialProperties();

    // app 端实现 oauth2 协议
    private OAuth2Properties oauth2 = new OAuth2Properties();

}
