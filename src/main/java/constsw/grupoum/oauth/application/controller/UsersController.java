package constsw.grupoum.oauth.application.controller;

import java.util.Collection;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import constsw.grupoum.oauth.application.exception.ApiException;
import constsw.grupoum.oauth.application.service.UserService;
import constsw.grupoum.oauth.integration.keycloak.record.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UsersController {

    private final UserService service;

    @GetMapping("/{id}")// não precisa escrever Bearer no inicio do access token, pois header em KeycloakService.userById() está completando
    public ResponseEntity<?> getUserById(@RequestHeader("Authorization2") String accessToken, @PathVariable String id) {
        try {
            log.info("GET -> /users/{id}");
            User user = service.finById(accessToken, id);
            log.info(String.format("GET -> /users/{id} RESPONSE: %s", user));

            return new ResponseEntity<User>(user, HttpStatus.OK);
        } catch (ApiException e) {
            log.error(e);
            return new ResponseEntity<String>(e.getMessage(), e.getStatus());
        }
    }
}
