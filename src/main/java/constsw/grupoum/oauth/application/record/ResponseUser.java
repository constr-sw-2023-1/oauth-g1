package constsw.grupoum.oauth.application.record;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ResponseUser(@JsonProperty("id") String id,
        @JsonProperty("username") String username,
        @JsonProperty("first_name") String firstName,
        @JsonProperty("last_name") String lastName,
        @JsonProperty("enabled") Boolean enabled) {
}
