package constsw.grupoum.oauth.integration.keycloak.exception;

import org.springframework.http.HttpStatus;

import constsw.grupoum.oauth.integration.keycloak.record.Error;

public class UserIdNaoEncontradoException extends KeycloakException {

    public UserIdNaoEncontradoException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, new Error("Usuario criado mas id nao retornado", ""));
    }

}
