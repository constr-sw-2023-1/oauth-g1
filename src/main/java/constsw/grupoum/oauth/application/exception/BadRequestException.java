package constsw.grupoum.oauth.application.exception;

import org.springframework.http.HttpStatus;

import constsw.grupoum.oauth.application.record.ResponseError;

public class BadRequestException extends ApiException {

    private static final HttpStatus STATUS = HttpStatus.BAD_REQUEST;

    private static final String CODE = "OA-002";

    private static final String DEAFULT_MESSAGE = "Requisicao mal formada";

    private static final ResponseError ERROR = new ResponseError(CODE, DEAFULT_MESSAGE);

    public BadRequestException() {
        super(STATUS, ERROR);
    }

    protected BadRequestException(Throwable cause) {
        super(STATUS, ERROR, cause);
    }

    @Override
    public Boolean applies(HttpStatus status, String fromMethod) {
        return STATUS.equals(status);
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
