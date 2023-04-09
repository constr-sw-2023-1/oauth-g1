package constsw.grupoum.oauth.application.service.impl;

import java.util.Collection;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import constsw.grupoum.oauth.application.exception.ApiException;
import constsw.grupoum.oauth.application.record.RequestNewUser;
import constsw.grupoum.oauth.application.record.ResponseNewUser;
import constsw.grupoum.oauth.application.service.UserService;
import constsw.grupoum.oauth.integration.keycloak.exception.KeycloakException;
import constsw.grupoum.oauth.integration.keycloak.record.RequestUserById;
import constsw.grupoum.oauth.integration.keycloak.record.RequestAllUsers;
import constsw.grupoum.oauth.integration.keycloak.record.RequestNewUserKeycloak;
import constsw.grupoum.oauth.integration.keycloak.record.User;
import constsw.grupoum.oauth.integration.keycloak.service.KeycloakService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    @Value("${integration.keycloak.realm}")
    String realm;

    private final KeycloakService keycloakService;

    @Override
    public Collection<User> findAll(String acessToken) throws ApiException {
        try {
            RequestAllUsers requestAllUsers = new RequestAllUsers(realm, acessToken);
            return keycloakService.getAllUsers(requestAllUsers);
        } catch (KeycloakException e) {
            throw new ApiException(e.getStatus(),
                    String.format("Erro: %s, Descricao: %s", e.getError().error(), e.getError().errorDescription()),
                    e);
        }
    }

    @Override
    public User finById(String authorization, String id) throws ApiException {
        try {
            RequestUserById requestUserById = new RequestUserById(realm, authorization, id);
            return keycloakService.userById(requestUserById);
        } catch (KeycloakException e) {
            throw new ApiException(e.getStatus(),
                    String.format("Erro: %s, Descricao: %s", e.getError().error(), e.getError().errorDescription()),
                    e);
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
            throw new ApiException(e.getStatus(),
                    String.format("Erro: %s, Descricao: %s", e.getError().error(), e.getError().errorDescription()),
                    e);
        }
    }

}
