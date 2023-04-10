package constsw.grupoum.oauth.integration.keycloak.record;

public record User(String id,
        String createdTimestamp,
        String username,
        Boolean enabled,
        Boolean emailVerified,
        String firstName,
        String lastName,
        String email) {
}
