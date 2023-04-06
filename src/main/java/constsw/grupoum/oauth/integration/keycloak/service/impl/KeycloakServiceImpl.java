package constsw.grupoum.oauth.integration.keycloak.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import constsw.grupoum.oauth.integration.keycloak.exception.KeycloakException;
import constsw.grupoum.oauth.integration.keycloak.record.Error;
import constsw.grupoum.oauth.integration.keycloak.record.RequestToken;
import constsw.grupoum.oauth.integration.keycloak.record.RequestUserById;
import constsw.grupoum.oauth.integration.keycloak.record.RequestUserInfo;
import constsw.grupoum.oauth.integration.keycloak.record.Token;
import constsw.grupoum.oauth.integration.keycloak.record.User;
import constsw.grupoum.oauth.integration.keycloak.record.UserInfo;
import constsw.grupoum.oauth.integration.keycloak.service.KeycloakService;

@Service
public class KeycloakServiceImpl implements KeycloakService {

    @Value("${integration.keycloak.url}")
    private String url;

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
                            .with("username", requestToken.username())
                            .with("password", requestToken.password())
                            .with("grant_type", requestToken.grantType())
                            .with("client_secret", requestToken.clientSecret()))
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
    public User userById(RequestUserById requestUserById) throws KeycloakException {
        try {

            return WebClient
                    .create(url)
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/admin/realms/{realm}/users/{id}")
                            .build(requestUserById.realm(),requestUserById.id()))
                    .header("Authorization", String.format("Bearer %s", requestUserById.acessToken()))
                    .retrieve()
                    .bodyToMono(User.class)
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
