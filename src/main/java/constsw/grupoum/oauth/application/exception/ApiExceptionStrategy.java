package constsw.grupoum.oauth.application.exception;

import org.springframework.http.HttpStatus;

public interface ApiExceptionStrategy<T extends ApiException> {

    Boolean applies(HttpStatus status, String fromMethod);

    T newException();

    T newException(Throwable cause);

}
