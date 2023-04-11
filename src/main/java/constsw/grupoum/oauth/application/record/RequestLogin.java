package constsw.grupoum.oauth.application.record;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RequestLogin(
        @JsonProperty("username") String username,
        @JsonProperty("password") String password) {
}
