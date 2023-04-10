package constsw.grupoum.oauth.application.service;

import constsw.grupoum.oauth.application.exception.ApiException;
import constsw.grupoum.oauth.application.record.RequestLogin;
import constsw.grupoum.oauth.application.record.RequestRefreshToken;
import constsw.grupoum.oauth.integration.keycloak.record.Token;

public interface TokenService {

    public Token retrieveTokenWithCredentials(RequestLogin requestLogin) throws ApiException;
    public Token retrieveTokenWithRefreshToken(RequestRefreshToken requestRefreshToken) throws ApiException;
}
