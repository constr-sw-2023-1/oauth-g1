package constsw.grupoum.oauth.integration.keycloak.record;

public record RequestToken(String realm,
                String clientId,
                String username,
                String password,
                String grantType,
                String clientSecret) {
}
