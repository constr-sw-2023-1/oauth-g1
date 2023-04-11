package constsw.grupoum.oauth.application.config;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import constsw.grupoum.oauth.application.exception.ApiException;
import constsw.grupoum.oauth.application.exception.BadRequestException;
import constsw.grupoum.oauth.application.exception.ForbiddenException;
import constsw.grupoum.oauth.application.exception.InternalServerErrorException;
import constsw.grupoum.oauth.application.exception.InvalidCredentialsException;
import constsw.grupoum.oauth.application.exception.NotFoundException;
import constsw.grupoum.oauth.application.exception.UnauthorizedException;

@Configuration
public class ApiExceptionsConfig {

    @Bean
    public Collection<ApiException> estrategias() {
        return Arrays.asList(new UnauthorizedException(),
                new BadRequestException(),
                new InternalServerErrorException(),
                new ForbiddenException(),
                new NotFoundException(),
                new InvalidCredentialsException());
    }

}
