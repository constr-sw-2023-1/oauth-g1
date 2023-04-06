package constsw.grupoum.oauth.integration.keycloak.record;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserInfo(@JsonProperty("sub") String sub,
        @JsonProperty("email_verified") Boolean emailVerified,
        @JsonProperty("name") String name,
        @JsonProperty("preferred_username") String preferredUsername,
        @JsonProperty("given_name") String givenName,
        @JsonProperty("family_name") String familyName,
        @JsonProperty("email") String email) {
}
