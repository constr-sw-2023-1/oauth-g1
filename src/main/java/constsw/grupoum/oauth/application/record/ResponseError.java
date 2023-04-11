package constsw.grupoum.oauth.application.record;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ResponseError(
        @JsonProperty("error_code") String errorCode,
        @JsonProperty("error_description") String errorDescription) {

}
