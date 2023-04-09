package constsw.grupoum.oauth.integration.keycloak.record;

public record RequestNewUserKeycloak(String username, String email, String firstName, String lastName, Boolean enabled) {

}
