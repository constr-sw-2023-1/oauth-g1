package constsw.grupoum.oauth.application.exception;

import org.springframework.http.HttpStatus;

import constsw.grupoum.oauth.application.exception.enumeration.TypeException;
import constsw.grupoum.oauth.application.record.ResponseError;

public class BadRequestException extends ApiException {

    private static final HttpStatus STATUS = HttpStatus.BAD_REQUEST;

    private static final TypeException TYPE = TypeException.ALL;

    private static final String CODE = "OA-401";

    private static final String DEAFULT_MESSAGE = "Requisicao invalida";

    private static final ResponseError ERROR = new ResponseError(CODE, DEAFULT_MESSAGE);

    public BadRequestException() {
        super(STATUS, TYPE, ERROR);
    }

    protected BadRequestException(Throwable cause) {
        super(STATUS, TYPE, ERROR, cause);
    }

    @Override
    public BadRequestException newException() {
        return new BadRequestException();
    }

    @Override
    public BadRequestException newException(Throwable cause) {
        return new BadRequestException(cause);
    }

}
