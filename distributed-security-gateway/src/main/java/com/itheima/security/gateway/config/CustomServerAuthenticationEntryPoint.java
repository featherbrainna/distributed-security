package com.itheima.security.gateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 王忠义
 * @version 1.0
 * @date 2023/7/30 12:55
 */
@Slf4j
public class CustomServerAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {
    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException ex) {
        log.error("发生了认证异常", ex);
        return Mono.defer(()->Mono.just(exchange.getResponse()))
                .flatMap(response -> {
                    try {
                        ((HttpServletResponse) response).sendRedirect("http://localhost:53020/uaa/oauth2/authorize?client_id=c1&response_type=code&scope=all+openid&redirect_uri=https://www.baidu.com");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    /*response.setStatusCode(HttpStatus.UNAUTHORIZED);
                    String body = "{\"code\":401,\"msg\":\"token不合法或过期\"}";
                    DataBuffer buffer = response.bufferFactory().wrap(body.getBytes(StandardCharsets.UTF_8));
                    return response.writeWith(Mono.just(buffer))
                            .doOnError(error -> DataBufferUtils.release(buffer));*/
                    return null;
                });
    }
}
