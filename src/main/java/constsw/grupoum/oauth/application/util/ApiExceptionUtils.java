package constsw.grupoum.oauth.application.util;

import java.util.Collection;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import constsw.grupoum.oauth.application.exception.ApiException;
import constsw.grupoum.oauth.application.exception.FilterException;
import constsw.grupoum.oauth.application.exception.InternalServerErrorException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class ApiExceptionUtils {

    private final Collection<ApiException> exceptionStrategies;

    public ApiException retrieve(HttpStatus status, Collection<FilterException> filtros) {
        return exceptionStrategies
                .stream()
                .filter(apiException -> apiException.applies(status, filtros))
                .findFirst()
                .orElse(new InternalServerErrorException());
    }

}
