package constsw.grupoum.oauth.application.service;

import constsw.grupoum.oauth.application.exception.ApiException;
import constsw.grupoum.oauth.integration.keycloak.record.User;

public interface UserService {

    User finById(String acessToken, String id) throws ApiException;
}
