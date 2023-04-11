package constsw.grupoum.oauth.util;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class HeadersUtils {

    public String getValue(HttpHeaders headers, String key) {
        List<String> h = headers.get(key);
        if (h != null)
            if (h.size() == 1)
                return h.get(0);
        return null;
    }

}
