package constsw.grupoum.oauth.application.exception;

import org.springframework.http.HttpStatus;

import constsw.grupoum.oauth.application.exception.enumeration.TypeException;
import constsw.grupoum.oauth.application.record.ResponseError;

public class UnauthorizedException extends ApiException {

    private static final HttpStatus STATUS = HttpStatus.UNAUTHORIZED;

    private static final TypeException TYPE = TypeException.ALL;

    private static final String CODE = "OA-001";

    private static final String DEAFULT_MESSAGE = "Não autorizado";

    private static final ResponseError ERROR = new ResponseError(CODE, DEAFULT_MESSAGE);

    public UnauthorizedException() {
        super(STATUS, TYPE, ERROR);
    }

    protected UnauthorizedException(Throwable cause) {
        super(STATUS, TYPE, ERROR, cause);
    }

    @Override
    public UnauthorizedException newException() {
        return new UnauthorizedException();
    }

    @Override
    public UnauthorizedException newException(Throwable cause) {
        return new UnauthorizedException(cause);
    }

}
