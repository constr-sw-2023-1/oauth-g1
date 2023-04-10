package constsw.grupoum.oauth.util;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import constsw.grupoum.oauth.application.exception.ApiException;

@Component
public class HeadersUtils {

    public String getValue(HttpHeaders headers, String key) throws ApiException {
        List<String> h = headers.get(key);
        if (h != null)
            if (h.size() == 1)
                return h.get(0);
            else
                throw new ApiException(HttpStatus.BAD_REQUEST, String.format("Multiplos %s", key));
        else
            throw new ApiException(HttpStatus.BAD_REQUEST, String.format("%s não informado", key));
    }

}
