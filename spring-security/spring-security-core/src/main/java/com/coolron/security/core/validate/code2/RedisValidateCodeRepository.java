package com.coolron.security.core.validate.code2;

import com.coolron.security.core.chonggou.ValidateCodeType;
import com.coolron.security.core.validate.code.ValidateCode;
import com.coolron.security.core.validate.code.ValidateCodeException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.concurrent.TimeUnit;

/**
 * 将验证码存放redis的类
 * @author zhailiang
 *
 * 测试路径 :
 * 获取验证码
 * localhost:80/code/sms?mobile=17671757026
 * 请求头 :
 * deviceId : 0804
 * Authorization 指定用户名和密码(配置文件中配置的security.oauth2.client.client-id 和 security.oauth2.client.client-secret)
 *
 * 手机登录
 * localhost:80/authentication/mobile?mobile=17671757026&smsCode=019743
 * 请求头 :
 * deviceId : 0804
 * Authorization 指定用户名和密码(配置文件中配置的security.oauth2.client.client-id 和 security.oauth2.client.client-secret)
 */
@Component
public class RedisValidateCodeRepository implements ValidateCodeRepository {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    /**
     * redis保存验证码
     */
    @Override
    public void save(ServletWebRequest request, ValidateCode code, ValidateCodeType type) {
        redisTemplate.opsForValue().set(buildKey(request, type), code, 30, TimeUnit.MINUTES);
    }

    /**
     * 获取redis中的验证码
     */
    @Override
    public ValidateCode get(ServletWebRequest request, ValidateCodeType type) {
        Object value = redisTemplate.opsForValue().get(buildKey(request, type));
        if (value == null) {
            return null;
        }
        return (ValidateCode) value;
    }


    /**
     * 删除redis中的验证码
     */
    @Override
    public void remove(ServletWebRequest request, ValidateCodeType type) {
        redisTemplate.delete(buildKey(request, type));
    }

    /**
     * 构建保存在redis中的key
     * @param request
     * @param type
     * @return
     */
    private String buildKey(ServletWebRequest request, ValidateCodeType type) {
        String deviceId = request.getHeader("deviceId");
        if (StringUtils.isBlank(deviceId)) {
            throw new ValidateCodeException("请在请求头中携带deviceId参数");
        }
        return "code:" + type.toString().toLowerCase() + ":" + deviceId;
    }
}
