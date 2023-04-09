package constsw.grupoum.oauth.integration.keycloak.record;

public record RequestToken(String realm,
        String clientId,
        String clientSecret,
        String username,
        String password,
        String refreshToken,
        String grantType) {
}
