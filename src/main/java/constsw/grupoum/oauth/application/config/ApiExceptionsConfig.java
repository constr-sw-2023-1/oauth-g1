package constsw.grupoum.oauth.application.config;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import constsw.grupoum.oauth.application.exception.ApiException;
import constsw.grupoum.oauth.application.exception.UnauthorizedException;

@Configuration
public class ApiExceptionsConfig {

    @Bean
    public Collection<ApiException> estrategias() {
        return Arrays.asList(new UnauthorizedException());
    }

}
