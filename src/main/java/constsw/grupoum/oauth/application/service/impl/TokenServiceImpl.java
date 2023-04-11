package constsw.grupoum.oauth.application.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import constsw.grupoum.oauth.application.exception.ApiException;
import constsw.grupoum.oauth.application.record.RequestLogin;
import constsw.grupoum.oauth.application.record.RequestRefreshToken;
import constsw.grupoum.oauth.application.service.TokenService;
import constsw.grupoum.oauth.application.util.ApiExceptionUtils;
import constsw.grupoum.oauth.integration.keycloak.exception.KeycloakException;
import constsw.grupoum.oauth.integration.keycloak.record.RequestToken;
import constsw.grupoum.oauth.integration.keycloak.record.Token;
import constsw.grupoum.oauth.integration.keycloak.service.KeycloakService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TokenServiceImpl implements TokenService {

    @Value("${integration.keycloak.realm}")
    private String realm;

    @Value("${integration.keycloak.client-id}")
    private String clientId;

    @Value("${integration.keycloak.client-secret}")
    private String clientSecret;

    private final ApiExceptionUtils apiExceptions;

    private final KeycloakService keycloakService;

    public Token retrieveTokenWithCredentials(RequestLogin requestLogin) throws ApiException {
        try {
            RequestToken request = new RequestToken(realm, clientId, clientSecret, requestLogin.username(),
                    requestLogin.password(),
                    "", "password");
            return keycloakService.token(request);
        } catch (KeycloakException e) {

            throw apiExceptions.retrieve(e.getStatus(), "tokenRetrieveTokenWithCredentials").newException(e);
        }
    }

    public Token retrieveTokenWithRefreshToken(RequestRefreshToken requestRefreshToken) throws ApiException {
        try {
            RequestToken request = new RequestToken(realm, clientId, clientSecret, "", "",
                    requestRefreshToken.refreshToken(),
                    "refresh_token");
            return keycloakService.token(request);
        } catch (KeycloakException e) {

            throw apiExceptions.retrieve(e.getStatus(), "tokenRetrieveTokenWithRefreshToken").newException(e);
        }
    }
}
