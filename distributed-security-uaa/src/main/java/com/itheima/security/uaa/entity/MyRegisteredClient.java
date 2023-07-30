package com.itheima.security.uaa.entity;

import lombok.Data;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.oauth2.server.authorization.util.SpringAuthorizationServerVersion;

import java.time.Instant;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * 自定义的客户端对象
 * @author 王忠义
 * @version 1.0
 * @date 2023/7/26 17:44
 */
@Data
public class MyRegisteredClient extends RegisteredClient {
    private static final long serialVersionUID = SpringAuthorizationServerVersion.SERIAL_VERSION_UID;
    private String id;
    private String clientId;
    private Instant clientIdIssuedAt;
    private String clientSecret;
    private Instant clientSecretExpiresAt;
    private String clientName;
    private Set<ClientAuthenticationMethod> clientAuthenticationMethods = Collections.singleton(ClientAuthenticationMethod.CLIENT_SECRET_BASIC);
    private Set<AuthorizationGrantType> authorizationGrantTypes = new HashSet<>();
    private Set<String> redirectUris = Collections.singleton("https://www.baidu.com");
    private Set<String> scopes;
    private ClientSettings clientSettings = ClientSettings.builder().requireAuthorizationConsent(true).build();
    private TokenSettings tokenSettings = TokenSettings.builder().build();

    {
        authorizationGrantTypes.add(AuthorizationGrantType.PASSWORD);
        authorizationGrantTypes.add(AuthorizationGrantType.AUTHORIZATION_CODE);
        authorizationGrantTypes.add(AuthorizationGrantType.REFRESH_TOKEN);
        authorizationGrantTypes.add(AuthorizationGrantType.CLIENT_CREDENTIALS);
        authorizationGrantTypes.add(AuthorizationGrantType.JWT_BEARER);
        authorizationGrantTypes.add(AuthorizationGrantType.IMPLICIT);
    }
}
