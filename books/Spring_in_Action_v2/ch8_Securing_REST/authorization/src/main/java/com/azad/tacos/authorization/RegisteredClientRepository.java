package com.azad.tacos.authorization;

import org.springframework.lang.Nullable;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

/***
 * One component that isn't provided by OAuth2AuthorizationServerConfiguration class is the client repository. A client
 * repository is analogous to a user details service or user repository, except that instead of maintaining details
 * about users, it maintains details about clients that might be asking for authorization on behalf of users. It is
 * defined by the RegisteredClientRepository interface.
 */
public interface RegisteredClientRepository {

    @Nullable
    RegisteredClient findById(String id);

    @Nullable
    RegisteredClient findByClientId(String clientId);
}
