package com.coolron.security.app.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2018/10/21.
 * 自定义登录成功的处理
 * 默认登陆成功是继续执行之前的请求
 *
 * 参考BasicAuthenticationFilter获取请求中的参数
 *
 * 在登录成功的成功处理器中进行生成Token的处理
 */
@Slf4j
@Component("coolronAuthenticationSuccessHandler")
//public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
// SavedRequestAwareAuthenticationSuccessHandler security 默认的处理器
public class CoolronAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

        @Autowired
        private ObjectMapper objectMapper;

        // 存储第三方信息 clientID .....
        @Autowired
        private ClientDetailsService clientDetailsService;

        @Autowired
        private AuthorizationServerTokenServices authorizationServerTokenServices;


        @SuppressWarnings("unchecked")
        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                            Authentication authentication) throws IOException, ServletException {

            log.info("登录成功");

            String header = request.getHeader("Authorization");

            // 请求头中 应该是以 Basic 开头
            if (header == null || !header.startsWith("Basic ")) {
                throw new UnapprovedClientAuthenticationException("请求头中无client信息");
            }

            // 抽取 解码 请求头  获取 clientId  clientSecret
            String[] tokens = extractAndDecodeHeader(header, request);
            assert tokens.length == 2;

            String clientId = tokens[0];
            String clientSecret = tokens[1];

            //封装clientDetails  第三方注册信息
            ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);

            if (clientDetails == null) {
                throw new UnapprovedClientAuthenticationException("clientId对应的配置信息不存在:" + clientId);
            } else if (!StringUtils.equals(clientDetails.getClientSecret(), clientSecret)) {
                throw new UnapprovedClientAuthenticationException("clientSecret不匹配:" + clientId);
            }
            /**
             * 标准的是四种授权模式
             * custom表示自定义的
             */
            TokenRequest tokenRequest = new TokenRequest(MapUtils.EMPTY_MAP, clientId, clientDetails.getScope(), "custom");

            OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);

            OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);

            OAuth2AccessToken token = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);

            response.setContentType("application/json;charset=UTF-8");
            // 将令牌通过 json的形式返回
            response.getWriter().write(objectMapper.writeValueAsString(token));

        }

        private String[] extractAndDecodeHeader(String header, HttpServletRequest request) throws IOException {

            // 去掉前面的  Basic 空格
            byte[] base64Token = header.substring(6).getBytes("UTF-8");
            byte[] decoded;
            try {
                decoded = Base64.decode(base64Token);
            } catch (IllegalArgumentException e) {
                throw new BadCredentialsException("Failed to decode basic authentication token");
            }

            String token = new String(decoded, "UTF-8");

            int delim = token.indexOf(":");

            if (delim == -1) {
                throw new BadCredentialsException("Invalid basic authentication token");
            }
            return new String[] { token.substring(0, delim), token.substring(delim + 1) };
        }

    }
