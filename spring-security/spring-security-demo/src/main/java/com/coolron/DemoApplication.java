package com.coolron;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: xf
 * @Date: 2018/10/24 14:22
 * @Description:   参看慕课网 和 CSDN
 * 慕课网链接:http://coding.imooc.com/class/134.html
 * csdn 链接: https://blog.csdn.net/zlh313_01/article/details/82914970
 */
@SpringBootApplication
// mapper 接口类扫描包配置
@MapperScan("com.coolron.*.dao")
// @EnableScheduling
@PropertySource(value = { "classpath:application.properties" })
@RestController
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class,args);
    }

    @GetMapping("/hello")
    public String test(){
        return "hello spring security";
    }
}
