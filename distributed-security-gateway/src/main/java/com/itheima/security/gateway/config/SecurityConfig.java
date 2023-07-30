package com.itheima.security.gateway.config;

import com.itheima.security.gateway.filter.AuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

/**
 * 网关的资源客户端权限拦截
 * @author 王忠义
 * @version 1.0
 * @date 2023/7/28 8:14
 */
@EnableWebFluxSecurity
public class SecurityConfig  {

    @Autowired
    private AuthFilter authFilter;

    /**
     * 自定jwt转换成JwtAuthenticationToken时的转换器。
     * 自定义了读取哪个claims域为权限列表，还自定义了权限列表前缀为""。
     * @return
     */
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter(){
        //1.创建jwtGrantedAuthoritiesConverter
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");//设置权限前缀
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("authorities");//设置读取的jwt属性

        //2.初始化一个jwtAuthenticationConverter
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        //设置自定义的jwtGrantedAuthoritiesConverter
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        //启用ResourceServer过滤器
        http.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
                // 认证成功后没有权限操作
//                .accessDeniedHandler()
                // 还没有认证时发生认证异常，比如token过期，token不合法
//                .bearerTokenAuthenticationEntryPoint(new CustomServerAuthenticationEntryPoint());



        return http.csrf().disable().authorizeHttpRequests()
                //网关过滤器放行/uaa/**
                .mvcMatchers("/uaa/**").permitAll()
                //网关过校验客户端权限对于/order/**
                .mvcMatchers("/order/**").hasAuthority("all")
                .anyRequest().permitAll()

                //不使用session
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .build();
    }
}
