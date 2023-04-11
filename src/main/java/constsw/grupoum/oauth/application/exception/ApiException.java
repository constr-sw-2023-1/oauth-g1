package constsw.grupoum.oauth.application.exception;

import java.util.Collection;

import org.springframework.http.HttpStatus;

import constsw.grupoum.oauth.application.exception.enumeration.TypeException;
import constsw.grupoum.oauth.application.record.ResponseError;
import lombok.Getter;

@Getter
public abstract class ApiException extends Exception implements ApiExceptionStrategy<ApiException> {

    protected final HttpStatus STATUS;

    protected final TypeException TYPE;

    protected final ResponseError ERROR;

    protected ApiException(HttpStatus status, TypeException type, ResponseError error) {
        super();
        this.STATUS = status;
        this.TYPE = type;
        this.ERROR = error;
    }

    protected ApiException(HttpStatus status, TypeException type, ResponseError error, String message) {
        super(message);
        this.STATUS = status;
        this.TYPE = type;
        this.ERROR = error;
    }

    protected ApiException(HttpStatus status, TypeException type, ResponseError error, Throwable cause) {
        super(cause);
        this.STATUS = status;
        this.TYPE = type;
        this.ERROR = error;
    }

    protected ApiException(HttpStatus status, TypeException type, ResponseError error, String message,
            Throwable cause) {
        super(message, cause);
        this.STATUS = status;
        this.TYPE = type;
        this.ERROR = error;
    }

    @Override
    public Boolean applies(HttpStatus status, Collection<FilterException> filtros) {

        FilterException filter = filtros
                .stream()
                .filter(f -> STATUS.equals(f.status()))
                .findFirst()
                .orElse(new FilterException(STATUS, TYPE));

        return STATUS.equals(status) && STATUS.equals(filter.status()) && TYPE.equals(filter.type());
    }
}
