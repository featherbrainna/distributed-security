package com.itheima.security.uaa;

import com.itheima.security.uaa.dao.ClientDao;
import com.itheima.security.uaa.entity.ClientEntity;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;

@SpringBootTest
class UaaApplicationTests {

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private ClientDao clientDao;

    @Resource
    private JdbcRegisteredClientRepository registeredClientRepo;

    @Test
    void test() {
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setClientName("c1");
        clientEntity.setClientId("c1");
        clientEntity.setClientSecret(passwordEncoder.encode("secret"));
        clientEntity.setClientSecretExpiresAt(LocalDateTime.now().plus(1,ChronoUnit.YEARS).toInstant(ZoneOffset.UTC));
        HashSet<String> set = new HashSet<>();
        set.add("all");
        set.add("openid");
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : set) {
            stringBuilder.append(s);
            stringBuilder.append(",");
        }
        stringBuilder.deleteCharAt(stringBuilder.length()-1);
        clientEntity.setScopes(stringBuilder.toString());
        clientDao.insert(clientEntity);
    }

    @Test
    void test1() {
        RegisteredClient registeredClient = RegisteredClient.withId("1684178170105384961")
                .clientId("c1")
                .clientName("c1")
                .clientSecret(passwordEncoder.encode("secret"))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .authorizationGrantType(AuthorizationGrantType.IMPLICIT)
                .authorizationGrantType(AuthorizationGrantType.PASSWORD)

                .redirectUri("https://www.baidu.com")
                .scope("all")
                .scope(OidcScopes.OPENID)
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                .tokenSettings(TokenSettings.builder().build()).build();

        registeredClientRepo.save(registeredClient);
    }
}
