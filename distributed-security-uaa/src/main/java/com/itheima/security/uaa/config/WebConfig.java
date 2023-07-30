package com.itheima.security.uaa.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 王忠义
 * @version 1.0
 * @date 2023/7/22 20:04
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/loginsuccess").setViewName("loginsuccess");
//        registry.addViewController("/uaa/login").setViewName("login");
//        registry.addViewController("/uaa/logout").setViewName("logout");
        registry.addViewController("/uaa/errorpage").setViewName("error");
    }
}
