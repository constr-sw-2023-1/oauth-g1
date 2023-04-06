package constsw.grupoum.oauth.integration.keycloak.record;

import java.util.Date;

public record User(String id,
        Date createdTimestamp,
        String username,
        Boolean enabled,
        Boolean emailVerified,
        String firstName,
        String lastName,
        String email) {
}
