package constsw.grupoum.oauth.integration.keycloak.exception;

import org.springframework.http.HttpStatus;

import constsw.grupoum.oauth.integration.keycloak.record.Error;
import lombok.Getter;

@Getter
public class KeycloakException extends Exception {

    private HttpStatus status;

    private Error error;

    public KeycloakException(HttpStatus status, Throwable cause) {
        super(cause);
        this.status = status;
    }

    public KeycloakException(HttpStatus status, Error error, Throwable cause) {
        super(cause);
        this.status = status;
        this.error = error;
    }

}
