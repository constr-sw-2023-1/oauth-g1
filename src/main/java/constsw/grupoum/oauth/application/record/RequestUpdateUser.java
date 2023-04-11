package constsw.grupoum.oauth.application.record;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RequestUpdateUser(
        @JsonProperty("first_name") String firstName,
        @JsonProperty("last_name") String lastName) {

}
