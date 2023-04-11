package constsw.grupoum.oauth.application.service.impl;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import constsw.grupoum.oauth.application.exception.ApiException;
import constsw.grupoum.oauth.application.exception.FilterException;
import constsw.grupoum.oauth.application.exception.enumeration.TypeException;
import constsw.grupoum.oauth.application.record.RequestNewUser;
import constsw.grupoum.oauth.application.record.RequestUpdateUser;
import constsw.grupoum.oauth.application.record.ResponseNewUser;
import constsw.grupoum.oauth.application.service.UserService;
import constsw.grupoum.oauth.application.util.ApiExceptionUtils;
import constsw.grupoum.oauth.integration.keycloak.exception.KeycloakException;
import constsw.grupoum.oauth.integration.keycloak.record.RequestAllUsers;
import constsw.grupoum.oauth.integration.keycloak.record.RequestDeleteUserById;
import constsw.grupoum.oauth.integration.keycloak.record.RequestNewPassword;
import constsw.grupoum.oauth.integration.keycloak.record.RequestNewUserKeycloak;
import constsw.grupoum.oauth.integration.keycloak.record.RequestUpdateUserKeycloak;
import constsw.grupoum.oauth.integration.keycloak.record.RequestUserById;
import constsw.grupoum.oauth.integration.keycloak.record.User;
import constsw.grupoum.oauth.integration.keycloak.service.KeycloakService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    @Value("${integration.keycloak.realm}")
    String realm;

    private final ApiExceptionUtils apiExceptions;

    private final KeycloakService keycloakService;

    @Override
    public Collection<User> findAll(String authorization, Boolean enabled) throws ApiException {
        try {
            RequestAllUsers requestAllUsers = new RequestAllUsers(realm, authorization, enabled);

            return keycloakService.getAllUsers(requestAllUsers);

        } catch (KeycloakException e) {
            throw apiExceptions
                    .retrieve(e.getStatus(),
                            Arrays.asList(new FilterException(HttpStatus.UNAUTHORIZED, TypeException.USERS)))
                    .newException(e);
        }
    }

    @Override
    public void deleteUser(String accessToken, String id) throws ApiException {
        try {
            RequestDeleteUserById requestDeleteUserById = new RequestDeleteUserById(realm, accessToken, id);
            keycloakService.deleteUser(requestDeleteUserById);
        } catch (KeycloakException e) {
            throw apiExceptions
                    .retrieve(e.getStatus(),
                            Arrays.asList(new FilterException(HttpStatus.UNAUTHORIZED, TypeException.USERS)))
                    .newException(e);
        }
    }

    public User finById(String authorization, String id) throws ApiException {
        try {
            RequestUserById requestUserById = new RequestUserById(realm, authorization, id);
            return keycloakService.userById(requestUserById);
        } catch (KeycloakException e) {
            throw apiExceptions
                    .retrieve(e.getStatus(),
                            Arrays.asList(new FilterException(HttpStatus.UNAUTHORIZED, TypeException.USERS)))
                    .newException(e);
        }
    }

    @Override
    public ResponseNewUser creatUser(String authorization, RequestNewUser request) throws ApiException {
        try {
            RequestNewUserKeycloak newUser = new RequestNewUserKeycloak(
                    request.username(),
                    request.email(), request.firstName(), request.lastName(), true);
            String userId = keycloakService.createUser(realm, authorization, newUser);
            return new ResponseNewUser(userId, request.username(), request.email(), request.firstName(),
                    request.lastName(), true);
        } catch (KeycloakException e) {
            throw apiExceptions
                    .retrieve(e.getStatus(),
                            Arrays.asList(new FilterException(HttpStatus.UNAUTHORIZED, TypeException.USERS)))
                    .newException(e);
        }
    }

    @Override
    public void updateUser(String authorization, String id, RequestUpdateUser request) throws ApiException {
        try {
            RequestUpdateUserKeycloak newUser = new RequestUpdateUserKeycloak(request.firstName(), request.lastName());
            keycloakService.updateUser(realm, authorization, id, newUser);
        } catch (KeycloakException e) {
            throw apiExceptions
                    .retrieve(e.getStatus(),
                            Arrays.asList(new FilterException(HttpStatus.UNAUTHORIZED, TypeException.USERS)))
                    .newException(e);
        }
    }

    @Override
    public void newPassword(String authorization, String id, String password) throws ApiException {
        try {
            keycloakService.newPassword(realm, authorization, id, new RequestNewPassword("password", password, false));
        } catch (KeycloakException e) {
            throw apiExceptions
                    .retrieve(e.getStatus(),
                            Arrays.asList(new FilterException(HttpStatus.UNAUTHORIZED, TypeException.USERS)))
                    .newException(e);
        }
    }

}
