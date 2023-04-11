package constsw.grupoum.oauth.application.record;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RequestNewUser(
        @JsonProperty("username") String username,
        @JsonProperty("email") String email,
        @JsonProperty("first_name") String firstName,
        @JsonProperty("last_name") String lastName) {

}
