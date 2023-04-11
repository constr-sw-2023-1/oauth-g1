package constsw.grupoum.oauth.application.exception;

import java.util.Collection;

import org.springframework.http.HttpStatus;

public interface ApiExceptionStrategy<T extends ApiException> {

    Boolean applies(HttpStatus status, Collection<FilterException> filtros);

    T newException();

    T newException(Throwable cause);

}
