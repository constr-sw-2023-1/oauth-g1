package constsw.grupoum.oauth.integration.keycloak.service.impl;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import constsw.grupoum.oauth.integration.keycloak.exception.KeycloakException;
import constsw.grupoum.oauth.integration.keycloak.exception.UserIdNaoEncontradoException;
import constsw.grupoum.oauth.integration.keycloak.record.Error;
import constsw.grupoum.oauth.integration.keycloak.record.RequestAllUsers;
import constsw.grupoum.oauth.integration.keycloak.record.RequestNewPassword;
import constsw.grupoum.oauth.integration.keycloak.record.RequestNewUserKeycloak;
import constsw.grupoum.oauth.integration.keycloak.record.RequestToken;
import constsw.grupoum.oauth.integration.keycloak.record.RequestUpdateUserKeycloak;
import constsw.grupoum.oauth.integration.keycloak.record.RequestUserById;
import constsw.grupoum.oauth.integration.keycloak.record.RequestUserInfo;
import constsw.grupoum.oauth.integration.keycloak.record.Token;
import constsw.grupoum.oauth.integration.keycloak.record.User;
import constsw.grupoum.oauth.integration.keycloak.record.UserInfo;
import constsw.grupoum.oauth.integration.keycloak.service.KeycloakService;
import constsw.grupoum.oauth.util.HeadersUtils;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class KeycloakServiceImpl implements KeycloakService {

    @Value("${integration.keycloak.url}")
    private String url;

    private final HeadersUtils headersUtils;

    private final Pattern USER_ID = Pattern.compile("(?<=(.*\\/users\\/)).*");

    @Override
    public Token token(RequestToken requestToken) throws KeycloakException {
        try {
            return WebClient
                    .create(url)
                    .post()
                    .uri(uriBuilder -> uriBuilder
                            .path("/realms/{realm}/protocol/openid-connect/token")
                            .build(requestToken.realm()))
                    .body(BodyInserters.fromFormData("client_id", requestToken.clientId())
                            .with("client_secret", requestToken.clientSecret())
                            .with("username", requestToken.username())
                            .with("password", requestToken.password())
                            .with("refresh_token", requestToken.refreshToken())
                            .with("grant_type", requestToken.grantType()))
                    .retrieve()
                    .bodyToMono(Token.class)
                    .block();

        } catch (WebClientRequestException e) {
            throw new KeycloakException(HttpStatus.INTERNAL_SERVER_ERROR, e);
        } catch (WebClientResponseException e) {
            throw new KeycloakException(HttpStatus.valueOf(e.getStatusCode().value()),
                    e.getResponseBodyAs(Error.class),
                    e);
        } catch (Exception e) {
            throw new KeycloakException(HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    @Override
    public UserInfo userInfo(RequestUserInfo requestUserInfo) throws KeycloakException {
        try {

            return WebClient
                    .create(url)
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/realms/{realm}/protocol/openid-connect/userinfo")
                            .build(requestUserInfo.realm()))
                    .header("Authorization", String.format("Bearer %s", requestUserInfo.token()))
                    .retrieve()
                    .bodyToMono(UserInfo.class)
                    .block();

        } catch (WebClientRequestException e) {
            throw new KeycloakException(HttpStatus.INTERNAL_SERVER_ERROR, e);
        } catch (WebClientResponseException e) {
            throw new KeycloakException(HttpStatus.valueOf(e.getStatusCode().value()),
                    e.getResponseBodyAs(Error.class),
                    e);
        } catch (Exception e) {
            throw new KeycloakException(HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    @Override
    public Collection<User> getAllUsers(RequestAllUsers requestAllUsers) throws KeycloakException {
        try {

            return WebClient
                    .create(url)
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/admin/realms/{realm}/users")
                            .queryParam("enabled", requestAllUsers.enabled())
                            .build(requestAllUsers.realm()))
                    .header("Authorization", requestAllUsers.authorization())
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<Collection<User>>() {
                    })
                    .block();

        } catch (WebClientRequestException e) {
            throw new KeycloakException(HttpStatus.INTERNAL_SERVER_ERROR, e);
        } catch (WebClientResponseException e) {
            throw new KeycloakException(HttpStatus.valueOf(e.getStatusCode().value()),
                    e.getResponseBodyAs(Error.class),
                    e);
        } catch (Exception e) {
            throw new KeycloakException(HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    public User userById(RequestUserById requestUserById) throws KeycloakException {
        try {

            return WebClient
                    .create(url)
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/admin/realms/{realm}/users/{id}")
                            .build(requestUserById.realm(), requestUserById.id()))
                    .header("Authorization", requestUserById.authorization())
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<User>() {
                    })
                    .block();

        } catch (WebClientRequestException e) {
            throw new KeycloakException(HttpStatus.INTERNAL_SERVER_ERROR, e);
        } catch (WebClientResponseException e) {
            throw new KeycloakException(HttpStatus.valueOf(e.getStatusCode().value()),
                    e.getResponseBodyAs(Error.class),
                    e);
        } catch (Exception e) {
            throw new KeycloakException(HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    @Override
    public String createUser(String realm, String authorization, RequestNewUserKeycloak user) throws KeycloakException {
        try {

            ResponseEntity<Void> response = WebClient
                    .create(url)
                    .post()
                    .uri(uriBuilder -> uriBuilder
                            .path("/admin/realms/{realm}/users")
                            .build(realm))
                    .header("Authorization", authorization)
                    .body(BodyInserters.fromValue(user))
                    .retrieve()
                    .toBodilessEntity()
                    .block();

            String location = headersUtils.getValue(response.getHeaders(), "Location");
            Matcher matcher = USER_ID.matcher(location);

            if (matcher.find())
                return matcher.group();

            throw new UserIdNaoEncontradoException();

        } catch (WebClientRequestException e) {
            throw new KeycloakException(HttpStatus.INTERNAL_SERVER_ERROR, e);
        } catch (WebClientResponseException e) {
            throw new KeycloakException(HttpStatus.valueOf(e.getStatusCode().value()),
                    e.getResponseBodyAs(Error.class),
                    e);
        } catch (KeycloakException e) {
            throw e;
        } catch (Exception e) {
            throw new KeycloakException(HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    @Override
    public void updateUser(String realm, String authorization, String id, RequestUpdateUserKeycloak user)
            throws KeycloakException {
        try {

            WebClient
                    .create(url)
                    .put()
                    .uri(uriBuilder -> uriBuilder
                            .path("/admin/realms/{realm}/users/{id}")
                            .build(realm, id))
                    .header("Authorization", authorization)
                    .body(BodyInserters.fromValue(user))
                    .retrieve()
                    .toBodilessEntity()
                    .block();

        } catch (WebClientRequestException e) {
            throw new KeycloakException(HttpStatus.INTERNAL_SERVER_ERROR, e);
        } catch (WebClientResponseException e) {
            throw new KeycloakException(HttpStatus.valueOf(e.getStatusCode().value()),
                    e.getResponseBodyAs(Error.class),
                    e);
        } catch (Exception e) {
            throw new KeycloakException(HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    @Override
    public void newPassword(String realm, String authorization, String id, RequestNewPassword requestNewPassword)
            throws KeycloakException {
        try {

            WebClient
                    .create(url)
                    .put()
                    .uri(uriBuilder -> uriBuilder
                            .path("/admin/realms/{realm}/users/{id}/reset-password")
                            .build(realm, id))
                    .header("Authorization", authorization)
                    .body(BodyInserters.fromValue(requestNewPassword))
                    .retrieve()
                    .toBodilessEntity()
                    .block();

        } catch (WebClientRequestException e) {
            throw new KeycloakException(HttpStatus.INTERNAL_SERVER_ERROR, e);
        } catch (WebClientResponseException e) {
            throw new KeycloakException(HttpStatus.valueOf(e.getStatusCode().value()),
                    e.getResponseBodyAs(Error.class),
                    e);
        } catch (Exception e) {
            throw new KeycloakException(HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

}
