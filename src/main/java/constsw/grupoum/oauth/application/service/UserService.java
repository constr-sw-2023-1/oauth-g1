package constsw.grupoum.oauth.application.service;

import java.util.Collection;

import constsw.grupoum.oauth.application.exception.ApiException;
import constsw.grupoum.oauth.application.record.RequestNewUser;
import constsw.grupoum.oauth.application.record.ResponseNewUser;
import constsw.grupoum.oauth.integration.keycloak.record.User;

public interface UserService {

    User finById(String acessToken, String id) throws ApiException;

    Collection<User> findAll(String authorization, Boolean enabled) throws ApiException;

    void deleteUser(String accessToken, String id) throws ApiException;

    ResponseNewUser creatUser(String authorization, RequestNewUser name) throws ApiException;
    void updateUser(String authorization, String id, RequestNewUser user) throws ApiException;
}   
