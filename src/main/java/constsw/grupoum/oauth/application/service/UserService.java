package constsw.grupoum.oauth.application.service;

import java.util.Collection;

import constsw.grupoum.oauth.application.exception.ApiException;
import constsw.grupoum.oauth.integration.keycloak.record.User;

public interface UserService {

    Collection<User> findAll(String acessToken) throws ApiException;

    void deleteUser(String accessToken, String id) throws ApiException;
}
