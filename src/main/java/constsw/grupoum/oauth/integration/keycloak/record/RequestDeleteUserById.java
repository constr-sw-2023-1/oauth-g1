package constsw.grupoum.oauth.integration.keycloak.record;

public record RequestDeleteUserById(String realm, String accessToken, String id) {
}