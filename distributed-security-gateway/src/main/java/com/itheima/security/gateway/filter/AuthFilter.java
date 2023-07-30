package com.itheima.security.gateway.filter;

import cn.hutool.core.codec.Base64;
import cn.hutool.json.JSONUtil;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

/**
 * 网关的全局过滤器处理授权服务器发送的Authentication解析并设置到请求为明文token
 * @author 王忠义
 * @version 1.0
 * @date 2023/7/29 7:58
 */
@Component
public class AuthFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //接收的token类型有误,没有认证
        if (!(authentication instanceof JwtAuthenticationToken)){
            //转发到认证中心
//            HttpServletResponse response = (HttpServletResponse) exchange.getResponse();
//            response.sendRedirect("http://localhost:53020/uaa/login");
            return null;
        }
        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;


        //获取当前用户身份信息
        Jwt principal = (Jwt) jwtAuthenticationToken.getPrincipal();
        String username = principal.getSubject();

        //获取当前用户权限信息
        List<String> authorities = principal.getClaimAsStringList("authorities");

        //把身份信息和权限信息放在json中，加入http的header中
        Map<String, Object> map = jwtAuthenticationToken.getTokenAttributes();
        map.put("principal",username);
        map.put("authorities",authorities);

        String encode = Base64.encode(JSONUtil.toJsonStr(map));
        request.getHeaders().add("json-token",encode);

        //转发给微服务
        return chain.filter(exchange);
    }
}
