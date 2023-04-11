package constsw.grupoum.oauth.application.exception;

import org.springframework.http.HttpStatus;

import constsw.grupoum.oauth.application.record.ResponseError;
import lombok.Getter;

@Getter
public abstract class ApiException extends Exception implements ApiExceptionStrategy<ApiException> {

    private HttpStatus status;

    private ResponseError error;

    protected ApiException(HttpStatus status, ResponseError error) {
        super();
        this.status = status;
        this.error = error;
    }

    protected ApiException(HttpStatus status, ResponseError error, String message) {
        super(message);
        this.status = status;
        this.error = error;
    }

    protected ApiException(HttpStatus status, ResponseError error, Throwable cause) {
        super(cause);
        this.status = status;
        this.error = error;
    }

    protected ApiException(HttpStatus status, ResponseError error, String message, Throwable cause) {
        super(message, cause);
        this.status = status;
        this.error = error;
    }
}
