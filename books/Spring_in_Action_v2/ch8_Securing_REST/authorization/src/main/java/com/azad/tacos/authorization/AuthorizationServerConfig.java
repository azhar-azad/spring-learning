package com.azad.tacos.authorization;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.web.SecurityFilterChain;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

@Configuration(proxyBeanMethods = false)
public class AuthorizationServerConfig {

    /***
     * The authorizationServerSecurityFilterChain() bean method defines a SecurityFilterChain that sets up some default
     * behavior for the OAuth 2 authorization server and a default form login page.
     * @Order - annotation is given Ordered.HIGHEST_PRECEDENCE to ensure that if for some reason there are other beans
     * of this type declared, this one takes precedence over the others.
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        return http
                .formLogin(Customizer.withDefaults())
                .build();
    }

    /***
     * In a production setting, we might write a custom implementations of RegisteredClientRepository to retrieve client
     * details from a database or from some other source. But out of the box, Spring Authorization Server offers an
     * in-memory implementation that is perfect for demonstration and testing purpose. For our purposes, we'll use the
     * in-memory implementation to register a single client with the authorization server.
     * There are a lot of details that go into a RegisteredClient.
     *      - ID - A random, unique identifier
     *      - Client ID - Analogous to a username, but instead of a user, it is a client. In this case, "taco-admin-client".
     *      - Client Secret - Analogous to a password for the client. Here we're using the word "secret" for the client
     *      secret.
     *      - Authorization grant type - The OAuth 2 grant types that this client will support. In this case, we're
     *      enabling authorization code and refresh token grants.
     *      - Redirect URL - One or more registered URLs that the authorization server can redirect to after
     *      authorization is granted. This adds another level of security, preventing some arbitrary application from
     *      receiving an authorization code that it could exchange for a token.
     *      - Scope - One or more OAuth 2 scopes that this client is allowed to ask for. Here we are setting three
     *      scopes: "writeIngredients", "deleteIngredients", and the constant OidcScopes.OPENID, which resolves to
     *      "openid". The "openid" scope will be necessary later when we use the authorization server as a single-sign-on
     *      solutions for the Taco Cloud admin application.
     *      - Client settings - This is a lambda that allows us to customize the client settings. In this case, we're
     *      requiring explicit user consent before granting the requested scope. Without this, the scope would be
     *      implicitly granted after the user logs in.
     * @param passwordEncoder
     * @return
     */
    @Bean
    public RegisteredClientRepository registeredClientRepository(PasswordEncoder passwordEncoder) {
        RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("taco-admin-client")
                .clientSecret(passwordEncoder.encode("secret"))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .redirectUri("http://127.0.0.1:9090/login/oauth2/code/taco-admin-client")
                .scope("writeIngredients")
                .scope("deleteIngredients")
                .scope(OidcScopes.OPENID)
                .clientSettings(clientSettings -> clientSettings.requireUserConsent(true))
                .build();
        return new InMemoryRegisteredClientRepository(registeredClient);
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() throws NoSuchAlgorithmException {
        RSAKey rsaKey = generateRsa();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }

    private static RSAKey generateRsa() throws NoSuchAlgorithmException {
        KeyPair keyPair = generateRsaKey();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        return new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();
    }

    private static KeyPair generateRsaKey() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.generateKeyPair();
    }

    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }
}
