package constsw.grupoum.oauth.application.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import constsw.grupoum.oauth.application.exception.ApiException;
import constsw.grupoum.oauth.application.service.UserService;
import constsw.grupoum.oauth.integration.keycloak.exception.KeycloakException;
import constsw.grupoum.oauth.integration.keycloak.record.RequestAllUsers;
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
    public User creatUser(String accessToken, User user) throws ApiException {
        try {
            return keycloakService.createUser(realm ,accessToken, user);
        } catch (KeycloakException e) {
            throw new ApiException(e.getStatus(),
            String.format("Erro: %s, Descricao: %s", e.getError().error(), e.getError().errorDescription()),
            e);
        }
    }

}
