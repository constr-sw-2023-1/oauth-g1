package constsw.grupoum.oauth.application.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import constsw.grupoum.oauth.application.exception.ApiException;
import constsw.grupoum.oauth.application.service.UserService;
import constsw.grupoum.oauth.integration.keycloak.exception.KeycloakException;
import constsw.grupoum.oauth.integration.keycloak.record.RequestAllUsers;
import constsw.grupoum.oauth.integration.keycloak.record.RequestDeleteUserById;
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
    public void deleteUser(String accessToken, String id) throws ApiException {
        try {
            RequestDeleteUserById requestDeleteUserById = new RequestDeleteUserById(realm, accessToken, id);
            keycloakService.deleteUser(requestDeleteUserById);
        } catch (KeycloakException e) {
            throw new ApiException(e.getStatus(),
                    String.format("Erro: %s, Descricao: %s", e.getError().error(), e.getError().errorDescription()),
                    e);
        }
    }

}
