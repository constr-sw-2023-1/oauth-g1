package constsw.grupoum.oauth.application.exception;

import org.springframework.http.HttpStatus;

import constsw.grupoum.oauth.application.record.ResponseError;

public class InternalServerErrorException extends ApiException {

    private static final HttpStatus STATUS = HttpStatus.INTERNAL_SERVER_ERROR;

    private static final String CODE = "OA-003";

    private static final String DEAFULT_MESSAGE = "Erro interno favor contatar professor Arruda";

    private static final ResponseError ERROR = new ResponseError(CODE, DEAFULT_MESSAGE);

    public InternalServerErrorException() {
        super(STATUS, ERROR);
    }

    protected InternalServerErrorException(Throwable cause) {
        super(STATUS, ERROR, cause);
    }

    @Override
    public Boolean applies(HttpStatus status, String fromMethod) {
        return STATUS.equals(status);
    }

    @Override
    public InternalServerErrorException newException() {
        return new InternalServerErrorException();
    }

    @Override
    public InternalServerErrorException newException(Throwable cause) {
        return new InternalServerErrorException(cause);
    }
}
