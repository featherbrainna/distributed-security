package com.itheima.security.order.config;

import com.itheima.security.order.filter.TokenAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author 王忠义
 * @version 1.0
 * @date 2023/7/28 8:14
 */
@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

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

    @Autowired
    private TokenAuthenticationFilter tokenAuthenticationFilter;

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable().authorizeHttpRequests()
//                .antMatchers("/order/r/r1").hasAuthority("SCOPE_all")
//                .antMatchers("/order/r/r2").hasAuthority("SCOPE_all")
                .anyRequest().permitAll()

                //启用ResourceServer过滤器
                .and().oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)

                //不使用session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and().addFilterAfter(tokenAuthenticationFilter, BearerTokenAuthenticationFilter.class)
                .build();
    }
}
