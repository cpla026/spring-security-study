package com.coolron.security.core.social;

import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * 自己创建ImoocSpringSocialConfigurer继承SpringSocialConfigurer，重写
 * postProcess，将自己配置的filterProcessesUrl设置进去。
 * @author zhailiang
 *
 */
public class CoolronSpringSocialConfigurer extends SpringSocialConfigurer {

        // 拦截 URL
        private String filterProcessesUrl;

        public CoolronSpringSocialConfigurer(String filterProcessesUrl) {
            this.filterProcessesUrl = filterProcessesUrl;
        }

        @Override
        protected <T> T postProcess(T object) {
            SocialAuthenticationFilter filter = (SocialAuthenticationFilter) super.postProcess(object);
            filter.setFilterProcessesUrl(filterProcessesUrl);
            return (T) filter;
        }

    }
