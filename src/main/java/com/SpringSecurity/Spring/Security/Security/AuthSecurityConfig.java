package com.SpringSecurity.Spring.Security.Security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.core.AuthorizationGrantType;



@Configuration
@EnableAuthorizationServer
public class AuthSecurityConfig  extends AuthorizationServerConfigurerAdapter {

    @Bean
    ClientRegistration githubClientRegistration(
            @Value("${spring.security.oauth2.client.provider.gitsec.token-uri}") String token_uri,
            @Value("${spring.security.oauth2.client.registration.gitsec.client-id}") String client_id,
            @Value("${spring.security.oauth2.client.registration.gitsec.client-secret}") String client_secret,
            @Value("${spring.security.oauth2.client.registration.gitsec.scope}") String scope,
            @Value("${spring.security.oauth2.client.registration.gitsec.authorization-grant-type}") String authorizationGrantType,
            @Value("${spring.security.oauth2.client.registration.gitsec.redirect-uri}") String redirect_uri,
            @Value("${spring.security.oauth2.client.provider.gitsec.authorization-uri}") String authorization_uri


    ) {
        return ClientRegistration
                .withRegistrationId("ggg")
                .tokenUri(token_uri)
                .clientId(client_id)
                .clientSecret(client_secret)
                .scope(scope)
                .redirectUri(redirect_uri)
                .authorizationUri(authorization_uri)
                .authorizationGrantType(new AuthorizationGrantType(authorizationGrantType))
                .build();
    }


    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
                .inMemory()
                .withClient("ggg")
                .secret("dbb3c5ecc0094bc8e02bbcc401dc6b8161072e04")
                .accessTokenValiditySeconds(3600)
                .authorizedGrantTypes("client_credentials","refresh-token")
                .scopes("read","write")
                .and().build();
    }

    // Create the client registration repository
    @Bean
    public ClientRegistrationRepository clientRegistrationRepository(ClientRegistration oktaClientRegistration) {
        return new InMemoryClientRegistrationRepository(oktaClientRegistration);
    }

    // Create the authorized client service
    @Bean
    public OAuth2AuthorizedClientService auth2AuthorizedClientService(ClientRegistrationRepository clientRegistrationRepository) {
        return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository);
    }

    // Create the authorized client manager and service manager using the
    // beans created and configured above
    @Bean
    public AuthorizedClientServiceOAuth2AuthorizedClientManager authorizedClientServiceAndManager (
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientService authorizedClientService) {

        OAuth2AuthorizedClientProvider authorizedClientProvider =
                OAuth2AuthorizedClientProviderBuilder.builder()
                        .clientCredentials()
                        .build();

        AuthorizedClientServiceOAuth2AuthorizedClientManager authorizedClientManager =
                new AuthorizedClientServiceOAuth2AuthorizedClientManager(
                        clientRegistrationRepository, authorizedClientService);
        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);

        return authorizedClientManager;
    }
}