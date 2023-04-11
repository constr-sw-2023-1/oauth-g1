package constsw.grupoum.oauth.application.exception;

import org.springframework.http.HttpStatus;

import constsw.grupoum.oauth.application.exception.enumeration.TypeException;
import constsw.grupoum.oauth.application.record.ResponseError;

public class ForbiddenException extends ApiException {

    private static final HttpStatus STATUS = HttpStatus.FORBIDDEN;

    private static final TypeException TYPE = TypeException.ALL;

    private static final String CODE = "OA-004";

    private static final String DEAFULT_MESSAGE = "Acesso proibido";

    private static final ResponseError ERROR = new ResponseError(CODE, DEAFULT_MESSAGE);

    public ForbiddenException() {
        super(STATUS, TYPE, ERROR);
    }

    protected ForbiddenException(Throwable cause) {
        super(STATUS, TYPE, ERROR, cause);
    }

    @Override
    public ForbiddenException newException() {
        return new ForbiddenException();
    }

    @Override
    public ForbiddenException newException(Throwable cause) {
        return new ForbiddenException(cause);
    }
}
