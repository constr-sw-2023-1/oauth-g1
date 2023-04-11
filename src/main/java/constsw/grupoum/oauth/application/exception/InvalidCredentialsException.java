package constsw.grupoum.oauth.application.exception;

import org.springframework.http.HttpStatus;

import constsw.grupoum.oauth.application.exception.enumeration.TypeException;
import constsw.grupoum.oauth.application.record.ResponseError;

public class InvalidCredentialsException extends ApiException {

    private static final HttpStatus STATUS = HttpStatus.UNAUTHORIZED;

    private static final TypeException TYPE = TypeException.LOGIN;

    private static final String CODE = "OA-003";

    private static final String DEAFULT_MESSAGE = "Credenciais invalidas";

    private static final ResponseError ERROR = new ResponseError(CODE, DEAFULT_MESSAGE);

    public InvalidCredentialsException() {
        super(STATUS, TYPE, ERROR);
    }

    protected InvalidCredentialsException(Throwable cause) {
        super(STATUS, TYPE, ERROR, cause);
    }

    @Override
    public InvalidCredentialsException newException() {
        return new InvalidCredentialsException();
    }

    @Override
    public InvalidCredentialsException newException(Throwable cause) {
        return new InvalidCredentialsException(cause);
    }
}
