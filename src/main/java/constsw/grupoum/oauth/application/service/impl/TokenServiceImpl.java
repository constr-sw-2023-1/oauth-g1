package constsw.grupoum.oauth.application.service.impl;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import constsw.grupoum.oauth.application.exception.ApiException;
import constsw.grupoum.oauth.application.exception.FilterException;
import constsw.grupoum.oauth.application.exception.enumeration.TypeException;
import constsw.grupoum.oauth.application.record.RequestLogin;
import constsw.grupoum.oauth.application.record.RequestRefreshToken;
import constsw.grupoum.oauth.application.record.ResponseToken;
import constsw.grupoum.oauth.application.service.TokenService;
import constsw.grupoum.oauth.application.util.ApiExceptionUtils;
import constsw.grupoum.oauth.integration.keycloak.exception.KeycloakException;
import constsw.grupoum.oauth.integration.keycloak.record.RequestToken;
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

    private final ObjectMapper mapper;

    private final ApiExceptionUtils apiExceptions;

    private final KeycloakService keycloakService;

    public ResponseToken retrieveTokenWithCredentials(RequestLogin requestLogin) throws ApiException {
        try {
            RequestToken request = new RequestToken(realm, clientId, clientSecret, requestLogin.username(),
                    requestLogin.password(),
                    "", "password");
            return mapper.convertValue(keycloakService.token(request), ResponseToken.class);

        } catch (KeycloakException e) {

            throw apiExceptions
                    .retrieve(e.getStatus(),
                            Arrays.asList(new FilterException(HttpStatus.UNAUTHORIZED, TypeException.LOGIN)))
                    .newException(e);
        }
    }

    public ResponseToken retrieveTokenWithRefreshToken(RequestRefreshToken requestRefreshToken) throws ApiException {
        try {
            RequestToken request = new RequestToken(realm, clientId, clientSecret, "", "",
                    requestRefreshToken.refreshToken(),
                    "refresh_token");
            return mapper.convertValue(keycloakService.token(request), ResponseToken.class);
        } catch (KeycloakException e) {

            throw apiExceptions.retrieve(e.getStatus(), new ArrayList<FilterException>()).newException(e);
        }
    }
}
