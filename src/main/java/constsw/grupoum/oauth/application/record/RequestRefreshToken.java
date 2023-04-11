package constsw.grupoum.oauth.application.record;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RequestRefreshToken(@JsonProperty("refresh_token") String refreshToken) {
}
