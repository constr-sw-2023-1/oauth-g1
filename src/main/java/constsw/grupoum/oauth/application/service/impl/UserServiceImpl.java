package constsw.grupoum.oauth.application.service.impl;

import java.util.Collection;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import constsw.grupoum.oauth.application.exception.ApiException;
import constsw.grupoum.oauth.application.service.UserService;
import constsw.grupoum.oauth.integration.keycloak.exception.KeycloakException;
import constsw.grupoum.oauth.integration.keycloak.record.NewUser;
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
    public void creatUser(String accessToken, String name) throws ApiException {
        try {
            NewUser newUser = new NewUser(name, generateRandomId(),true); 
            keycloakService.createUser(realm ,accessToken, newUser);
        } catch (KeycloakException e) {
            throw new ApiException(e.getStatus(),
            String.format("Erro: %s, Descricao: %s", e.getError().error(), e.getError().errorDescription()),
            e);
        }
    }


    public String generateRandomId() {
        return UUID.randomUUID().toString();
    }
}
