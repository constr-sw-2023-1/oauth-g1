package constsw.grupoum.oauth.integration.keycloak.service;

import constsw.grupoum.oauth.integration.keycloak.exception.KeycloakException;
import constsw.grupoum.oauth.integration.keycloak.record.RequestToken;
import constsw.grupoum.oauth.integration.keycloak.record.RequestUserInfo;
import constsw.grupoum.oauth.integration.keycloak.record.Token;
import constsw.grupoum.oauth.integration.keycloak.record.UserInfo;

public interface KeycloakService {

    Token token(RequestToken requestToken) throws KeycloakException;

    UserInfo userInfo(RequestUserInfo requestUserInfo) throws KeycloakException;

}
