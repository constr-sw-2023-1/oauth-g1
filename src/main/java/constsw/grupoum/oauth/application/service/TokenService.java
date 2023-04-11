package constsw.grupoum.oauth.application.service;

import constsw.grupoum.oauth.application.exception.ApiException;
import constsw.grupoum.oauth.application.record.RequestLogin;
import constsw.grupoum.oauth.application.record.RequestRefreshToken;
import constsw.grupoum.oauth.application.record.ResponseToken;

public interface TokenService {

    public ResponseToken retrieveTokenWithCredentials(RequestLogin requestLogin) throws ApiException;

    public ResponseToken retrieveTokenWithRefreshToken(RequestRefreshToken requestRefreshToken) throws ApiException;
}
