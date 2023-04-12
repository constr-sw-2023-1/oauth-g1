package constsw.grupoum.oauth.integration.keycloak.record;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Error(@JsonProperty("error") String error,
        @JsonProperty("error_description") String errorDescription) {
}
