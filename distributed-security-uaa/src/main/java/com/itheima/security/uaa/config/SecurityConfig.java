package com.itheima.security.uaa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author 王忠义
 * @version 1.0
 * @date 2023/7/26 22:47
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * 此过滤器链在AuthorizationServer过滤器链的后面，即关于AuthorizationServer的端点无法在这里设置安全规则
     * A Spring Security filter chain for authentication
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    @Order(2)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception{
        return http.authorizeHttpRequests()
                .mvcMatchers("/uaa/errorpage").permitAll()
                .anyRequest().authenticated()

                .and()
                .formLogin()

                .and()
                .logout()

                .and()
                .csrf().disable().build();
    }
}
