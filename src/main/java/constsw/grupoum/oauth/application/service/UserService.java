package constsw.grupoum.oauth.application.service;

import java.util.Collection;

import constsw.grupoum.oauth.application.exception.ApiException;
import constsw.grupoum.oauth.application.record.RequestNewUser;
import constsw.grupoum.oauth.application.record.RequestUpdateUser;
import constsw.grupoum.oauth.application.record.ResponseNewUser;
import constsw.grupoum.oauth.application.record.ResponseUser;

public interface UserService {

    ResponseUser findById(String acessToken, String id) throws ApiException;

    Collection<ResponseUser> findAll(String authorization, Boolean enabled) throws ApiException;

    void deleteUser(String authorization, String id) throws ApiException;

    ResponseNewUser createUser(String authorization, RequestNewUser name) throws ApiException;

    void updateUser(String authorization, String id, RequestUpdateUser user) throws ApiException;

    void newPassword(String authorization, String id, String password) throws ApiException;
}
