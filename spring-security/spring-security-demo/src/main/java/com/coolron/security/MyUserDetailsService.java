package com.coolron.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

/**
 * @Auther: xf
 * @Date: 2018/10/24 16:22
 * @Description: Security 安全认证的 userservice
 */
@Slf4j
@Component("userDetailsService") // 使之成为spring 中的bean
public class MyUserDetailsService implements UserDetailsService, SocialUserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 表单登录用户名
     * @param username   用户名
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("表单登录用户名: " + username);
        return buildUser(username);
    }

    /**
     *
     * @param userId spring social 根据 openId 查询出来的 userId
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        log.info("社交登录用户ID: " + userId);
        return buildUser(userId);
    }

    private SocialUserDetails buildUser(String userId){
        String password = passwordEncoder.encode("123456");
        log.info("数据库密码是:" + password);

        // 根据查找到的信息判断用户是否被冻结   参看 User 的构造
        // 添加ROLE_USER的角色（默认必须有这个角色）
        return new SocialUser(userId, password,
                true, true, true, true,
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin,ROLE_USER"));
    }
}
