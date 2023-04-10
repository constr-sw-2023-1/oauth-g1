package constsw.grupoum.oauth.integration.keycloak.record;

public record User(String id,
                String username,
                Boolean enabled,
                String firstName,
                String lastName) {
}
