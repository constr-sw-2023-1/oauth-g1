package constsw.grupoum.oauth.integration.keycloak.record;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RequestToken(String realm,
        String clientId,
        String username,
        String password,
        @JsonProperty("grant_type")
        String grantType,
        String clientSecret) {
}
