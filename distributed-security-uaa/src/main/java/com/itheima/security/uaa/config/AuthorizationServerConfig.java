package com.itheima.security.uaa.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 认证服务器配置
 * @author 王忠义
 * @version 1.0
 * @date 2023/7/26 15:16
 */
@Configuration(proxyBeanMethods = false)
@Slf4j
public class AuthorizationServerConfig{

    /**
     * 一、个性化 JWT token
     * 定制access_tokken的claims内容
     */
    @Bean
    public CustomOAuth2TokenCustomizer jwtTokenCustomizer(){
        return new CustomOAuth2TokenCustomizer();
    }

    class CustomOAuth2TokenCustomizer implements OAuth2TokenCustomizer<JwtEncodingContext> {

        @Override
        public void customize(JwtEncodingContext context) {
            //1.获取客户端权限
            Set<String> scopes = context.getAuthorizedScopes();
            //2.获取用户权限
            Collection<? extends GrantedAuthority> authorities = context.getPrincipal().getAuthorities();
            //转换用户权限
            List<String> list = authorities.stream()
                    .map(GrantedAuthority::getAuthority).collect(Collectors.toList());

            //3.组合客户端权限和用户权限
            list.addAll(scopes);

            //4.添加一个自定义头
            JwtClaimsSet.Builder claims = context.getClaims();
            claims.claim("authorities",list);
        }
    }

    /**
     * 二、oauth2.0协议过滤器组件，实现协议处理
     * A Spring Security filter chain for the Protocol Endpoints.
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    @Order(1)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
        //1.创建授权服务器配置类
        OAuth2AuthorizationServerConfigurer serverConfigurer = new OAuth2AuthorizationServerConfigurer();

        //2.授权端点错误处理（OAuth2AuthorizationEndpointFilter）
        serverConfigurer.authorizationEndpoint(config -> {
            config.errorResponseHandler((request, response, exception) -> {
                OAuth2AuthenticationException oAuth2AuthenticationException = (OAuth2AuthenticationException) exception;
                OAuth2Error error = oAuth2AuthenticationException.getError();
                log.error("错误原因:[{}]", error);

                log.info("认证异常", exception);
                response.sendRedirect("http://localhost:53020/uaa/errorpage");
                /* response.setCharacterEncoding(StandardCharsets.UTF_8.name());
                response.setContentType(MediaType.APPLICATION_JSON.toString());
                response.getWriter().write("{\"code\":-1,\"msg\":\"认证失败\"}");*/
            });
        });

        //3.token端点错误处理（OAuth2TokenEndpointFilter）
        serverConfigurer.tokenEndpoint(config -> {
            config.errorResponseHandler((request, response, exception) -> {
                OAuth2AuthenticationException oAuth2AuthenticationException = (OAuth2AuthenticationException) exception;
                OAuth2Error error = oAuth2AuthenticationException.getError();
                log.error("错误原因:[{}]", error);

                log.info("认证异常", exception);
                response.sendRedirect("http://localhost:53020/uaa/errorpage");
            });
        });

        //4.客户端认证过滤器错误处理（OAuth2ClientAuthenticationFilter）
        serverConfigurer.clientAuthentication(config -> {
            config.errorResponseHandler((request, response, exception) -> {
                OAuth2AuthenticationException oAuth2AuthenticationException = (OAuth2AuthenticationException) exception;
                OAuth2Error oAuth2Error = oAuth2AuthenticationException.getError();
                switch (oAuth2Error.getErrorCode()) {
                    case OAuth2ErrorCodes.INVALID_CLIENT:
                        log.info("未知的客户端");
                        break;
                    case OAuth2ErrorCodes.ACCESS_DENIED:
                        log.info("您无权限访问");
                        break;
                    default:
                        break;
                }
                log.error("错误原因:[{}]", oAuth2Error);

                log.info("认证异常", exception);
                response.setCharacterEncoding(StandardCharsets.UTF_8.name());
                response.setContentType(MediaType.APPLICATION_JSON.toString());
                response.getWriter().write("{\"code\":-2,\"msg\":\"客户端认证失败\"}");
            });
        });

        //5. Enable OpenID Connect 1.0
        serverConfigurer.oidc(Customizer.withDefaults());

        //6.可开启资源服务器
//        http.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);

        //6.端点安全规则配置，及各个端点配置的apply(可以使用OAuth2AuthorizationServerConfiguration简化代码)
        //OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http)框架提供的授权服务器的端点安全规则默认配置,此过滤器只会过滤指定的端点路径。
        RequestMatcher endpointsMatcher = serverConfigurer.getEndpointsMatcher();
        //要过滤的端点定义在endpointsMatcher
        return http.requestMatcher(endpointsMatcher)
                //端点安全配置
                .authorizeHttpRequests().anyRequest().authenticated()
                //端点csrf配置
                .and().csrf(csrf->csrf.ignoringRequestMatchers(endpointsMatcher))
                //使以上serverConfigurer配置生效
                .apply(serverConfigurer)
                //定义这些端点的登录页
                .and().formLogin()
                .and().build();
    }

    /**
     * 三、注册客户端应用, 对应 oauth2_registered_client 表
     */
    @Bean
    public RegisteredClientRepository registeredClientRepository(JdbcTemplate jdbcTemplate) {
        //1.配置一个默认的内部客户端gateway
        RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
                // 客户端id 需要唯一
                .clientId("gateway")
                // 客户端密码
                .clientSecret("gateway123")
                // 可以基于 basic 的方式和授权服务器进行认证
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                // 授权码
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                // 刷新token
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                // 客户端模式
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                // 密码模式
                .authorizationGrantType(AuthorizationGrantType.PASSWORD)
                // 简化模式，已过时，不推荐
                .authorizationGrantType(AuthorizationGrantType.IMPLICIT)
                // 重定向url
                .redirectUri("https://www.baidu.com")
                // 客户端申请的作用域，也可以理解这个客户端申请访问用户的哪些信息，比如：获取用户信息，获取用户照片等
                .scope("all")
                .clientSettings(
                        ClientSettings.builder()
                                // 是否需要用户确认一下客户端需要获取用户的哪些权限
                                // 比如：客户端需要获取用户的 用户信息、用户照片 但是此处用户可以控制只给客户端授权获取 用户信息。
                                .requireAuthorizationConsent(true)
                                .build()
                )
                .tokenSettings(
                        TokenSettings.builder()
                                // accessToken 的有效期
                                .accessTokenTimeToLive(Duration.ofHours(1))
                                // refreshToken 的有效期
                                .refreshTokenTimeToLive(Duration.ofDays(3))
                                // 是否可重用刷新令牌
                                .reuseRefreshTokens(true)
                                .build()
                )
                .build();

        //2.给存储器添加默认的客户端并返回
        JdbcRegisteredClientRepository repository = new JdbcRegisteredClientRepository(jdbcTemplate);
        repository.save(registeredClient);

        return repository;
    }

    /**
     * 令牌的发放记录, 对应 oauth2_authorization 表
     * 令牌存储，如果自定义UserDetails会存在反序列化问题
     */
    /*@Bean
    public OAuth2AuthorizationService authorizationService(JdbcTemplate jdbcTemplate, RegisteredClientRepository registeredClientRepository) {
        return new JdbcOAuth2AuthorizationService(jdbcTemplate, registeredClientRepository);
    }*/

    /**
     * 把资源拥有者授权确认操作保存到数据库, 对应 oauth2_authorization_consent 表
     */
   /* @Bean
    public OAuth2AuthorizationConsentService authorizationConsentService(JdbcTemplate jdbcTemplate, RegisteredClientRepository registeredClientRepository) {
        return new JdbcOAuth2AuthorizationConsentService(jdbcTemplate, registeredClientRepository);
    }*/

    /**
     * 四、签名token组件
     * An instance of com.nimbusds.jose.jwk.source.JWKSource for signing access tokens
     * @return
     */
    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        KeyPair keyPair = generateRsaKey();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAKey rsaKey = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return new ImmutableJWKSet<>(jwkSet);
    }

    /**
     * 此方法在jwkSource方法中调用生成KeyPair
     * An instance of java.security.KeyPair with keys generated on startup used to create the JWKSource above
     * @return
     */
    private static KeyPair generateRsaKey() {
        KeyPair keyPair;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
        }
        catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
        return keyPair;
    }

    /**
     * 解签jwt组件，实现解签。授权服务器解签目的：1.查询用户信息2.注册客户端
     * An instance of JwtDecoder for decoding signed access tokens
     *
     * a convenience (static) utility method that can be used to register a JwtDecoder @Bean,
     * which is REQUIRED for the OpenID Connect 1.0 UserInfo endpoint
     * and the OpenID Connect 1.0 Client Registration endpoint.
     * @param jwkSource
     * @return
     */
    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    /**
     * 五、客户端设置组件
     * @return
     */
    @Bean
    public ClientSettings clientSettings(){
        return ClientSettings.builder()
                .requireAuthorizationConsent(true)
                .build();
    }

    /**
     * 六、令牌设置组件
     * @return
     */
    @Bean
    public TokenSettings tokenSettings(){
        return TokenSettings.builder()
                .accessTokenTimeToLive(Duration.ofHours(2))//令牌有效期
                .refreshTokenTimeToLive(Duration.ofDays(3))//刷新令牌有效期
                .build();
    }

    /**
     * 七、uri端点路径设置组件
     * 设置授权服务器组件,可以设置所有端点的uri
     * An instance of AuthorizationServerSettings to configure Spring Authorization Server
     * @return
     */
    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder()
                .build();
    }
}
