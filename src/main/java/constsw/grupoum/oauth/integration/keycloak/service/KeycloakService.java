package constsw.grupoum.oauth.integration.keycloak.service;

import java.util.Collection;

import constsw.grupoum.oauth.integration.keycloak.exception.KeycloakException;
import constsw.grupoum.oauth.integration.keycloak.record.RequestNewUserKeycloak;
import constsw.grupoum.oauth.integration.keycloak.record.RequestAllUsers;
import constsw.grupoum.oauth.integration.keycloak.record.RequestDeleteUserById;
import constsw.grupoum.oauth.integration.keycloak.record.RequestToken;
import constsw.grupoum.oauth.integration.keycloak.record.RequestUserById;
import constsw.grupoum.oauth.integration.keycloak.record.RequestUserInfo;
import constsw.grupoum.oauth.integration.keycloak.record.Token;
import constsw.grupoum.oauth.integration.keycloak.record.User;
import constsw.grupoum.oauth.integration.keycloak.record.UserInfo;

public interface KeycloakService {

    Token token(RequestToken requestToken) throws KeycloakException;

    UserInfo userInfo(RequestUserInfo requestUserInfo) throws KeycloakException;

    Collection<User> getAllUsers(RequestAllUsers requestAllUsers) throws KeycloakException;

    void deleteUser(RequestDeleteUserById requestDeleteUserById) throws KeycloakException;

    User userById(RequestUserById requestUserById) throws KeycloakException;

    String createUser(String realm, String accessToken, RequestNewUserKeycloak user) throws KeycloakException;

}
