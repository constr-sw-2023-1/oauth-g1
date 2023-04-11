package constsw.grupoum.oauth.application.exception;

import org.springframework.http.HttpStatus;

import constsw.grupoum.oauth.application.exception.enumeration.TypeException;
import constsw.grupoum.oauth.application.record.ResponseError;

public class NotFoundException extends ApiException {

    private static final HttpStatus STATUS = HttpStatus.NOT_FOUND;

    private static final TypeException TYPE = TypeException.ALL;

    private static final String CODE = "OA-005";

    private static final String DEAFULT_MESSAGE = "Não encontrado";

    private static final ResponseError ERROR = new ResponseError(CODE, DEAFULT_MESSAGE);

    public NotFoundException() {
        super(STATUS, TYPE, ERROR);
    }

    protected NotFoundException(Throwable cause) {
        super(STATUS, TYPE, ERROR, cause);
    }

    @Override
    public NotFoundException newException() {
        return new NotFoundException();
    }

    @Override
    public NotFoundException newException(Throwable cause) {
        return new NotFoundException(cause);
    }

}
