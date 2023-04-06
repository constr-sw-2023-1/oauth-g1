package constsw.grupoum.oauth.application.controller;

import java.util.Collection;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping
    public ResponseEntity<?> getAllUsers(@RequestHeader("Authorization") String accessToken) {
        try {
            log.info("GET -> /users");
            Collection<User> users = service.findAll(accessToken);
            log.info(String.format("GET -> /users RESPONSE: %s", users));

            return new ResponseEntity<Collection<User>>(users, HttpStatus.OK);
        } catch (ApiException e) {
            log.error(e);
            return new ResponseEntity<String>(e.getMessage(), e.getStatus());
        }
    }

}
