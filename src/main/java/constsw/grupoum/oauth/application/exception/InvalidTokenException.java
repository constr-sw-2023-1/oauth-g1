package constsw.grupoum.oauth.application.exception;

import org.springframework.http.HttpStatus;

import constsw.grupoum.oauth.application.exception.enumeration.TypeException;
import constsw.grupoum.oauth.application.record.ResponseError;

public class InvalidTokenException extends ApiException {

    private static final HttpStatus STATUS = HttpStatus.UNAUTHORIZED;

    private static final TypeException TYPE = TypeException.USERS;

    private static final String CODE = "OA-102";

    private static final String DEAFULT_MESSAGE = "Token invalido ou expirado";

    private static final ResponseError ERROR = new ResponseError(CODE, DEAFULT_MESSAGE);

    public InvalidTokenException() {
        super(STATUS, TYPE, ERROR);
    }

    protected InvalidTokenException(Throwable cause) {
        super(STATUS, TYPE, ERROR, cause);
    }

    @Override
    public InvalidTokenException newException() {
        return new InvalidTokenException();
    }

    @Override
    public InvalidTokenException newException(Throwable cause) {
        return new InvalidTokenException(cause);
    }
}
