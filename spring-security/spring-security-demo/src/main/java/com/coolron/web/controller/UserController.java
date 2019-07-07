package com.coolron.web.controller;

import com.coolron.common.utils.HttpResult;
import com.coolron.security.core.properties.SecurityProperties;
import com.coolron.web.domain.User;
import com.coolron.web.service.UserService;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 *
 * @author 程序猿DD
 * @version 1.0.0
 * @blog http://blog.coolron.com
 *
 */
@RestController
@RequestMapping(value="/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProviderSignInUtils providerSignInUtils;

    @Autowired
    private SecurityProperties securityProperties;

    @GetMapping("/me")
    public Object getCurrentUser(Authentication user, HttpServletRequest request) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException, UnsupportedEncodingException {
        String header = request.getHeader("Authorization");
        // 截取token
        String token = StringUtils.substringAfter(header, "bearer ");

        Claims claims = Jwts.parser()  // 解析器
                // 签名的时候 使用的是UTF-8 jwt 验签的时候并不是使用的 UTF-8 所以需要处理
                .setSigningKey(securityProperties.getOauth2().getJwtSigningKey().getBytes("UTF-8")) // 指定密签
                .parseClaimsJws(token).getBody();  // 解析成 Claims 对象

        String company = (String) claims.get("company");
        String rose = (String) claims.get("rose");

        log.info(company);
        log.info(rose);

        return user;
    }


    /**
     * 注册用户
     *
     * 在用户进行第三方用户和QQ用户进行注册绑定时将用户唯一标示给Social,并插入UsersConnection中：
     * @param user
     * @return
     */
    @RequestMapping(value="regist", method=RequestMethod.POST)
    public void regist(@RequestBody User user, HttpServletRequest request) {

        // 不管是注册用户还是绑定用户, 都会拿到用户的唯一标识.
        String userId = user.getName();
        providerSignInUtils.doPostSignUp(userId,new ServletWebRequest(request));

    }

    @RequestMapping(value={"getUserList"}, method=RequestMethod.GET)
    public HttpResult getUserList() {
        List<User> userList = userService.getAllUser();
        return HttpResult.ok(userList);
    }

    @RequestMapping(value="addUser", method=RequestMethod.POST)
    public HttpResult addUser(@RequestBody User user) {

        int result = userService.addUser(user);

        return HttpResult.ok(result);
    }

    @RequestMapping(value="getUser/{id}", method=RequestMethod.GET)
    public HttpResult getUser(@PathVariable Integer id) {
        User user = userService.getUser(id);
        return HttpResult.ok(user);
    }

    @RequestMapping(value="updateUser/{id}", method=RequestMethod.PUT)
    public HttpResult putUser(@PathVariable Integer id, @RequestBody User user) {
        int result = userService.updateUser(user);
        return HttpResult.ok(result);
    }

    @RequestMapping(value="delete/{id}", method=RequestMethod.DELETE)
    public HttpResult deleteUser(@PathVariable Integer id) {
        int result = userService.deleteUser(id);
        return HttpResult.ok(result);
    }

}