package constsw.grupoum.oauth.application.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class ApiException extends Exception {

    private HttpStatus status;

    public ApiException(HttpStatus status) {
        super();
        this.status = status;
    }

    public ApiException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public ApiException(HttpStatus status, Throwable cause) {
        super(cause);
        this.status = status;
    }

    public ApiException(HttpStatus status, String message, Throwable cause) {
        super(message, cause);
        this.status = status;
    }
}
