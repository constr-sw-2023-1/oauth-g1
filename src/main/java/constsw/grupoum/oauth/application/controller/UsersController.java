package constsw.grupoum.oauth.application.controller;

import java.util.Collection;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import constsw.grupoum.oauth.application.exception.ApiException;
import constsw.grupoum.oauth.application.service.UserService;
import constsw.grupoum.oauth.application.util.HeadersUtils;
import constsw.grupoum.oauth.integration.keycloak.record.User;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/users")
public class UsersController {

    private final HeadersUtils headersUtils;

    private final UserService service;

    @GetMapping
    public ResponseEntity<?> getAllUsers(@RequestHeader HttpHeaders headers) {
        try {

            log.info("GET -> /users");
            Collection<User> users = service.findAll(headersUtils.getValue(headers, HttpHeaders.AUTHORIZATION));
            log.info(String.format("GET -> /users RESPONSE: %s", users));

            return new ResponseEntity<Collection<User>>(users, HttpStatus.OK);
        } catch (ApiException e) {
            log.error(e);
            return new ResponseEntity<String>(e.getMessage(), e.getStatus());
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getUserById(@RequestHeader HttpHeaders headers, @PathVariable String id) {
        try {

            log.info("GET -> /users/{id}");
            User user = service.finById(headersUtils.getValue(headers, HttpHeaders.AUTHORIZATION), id);
            log.info(String.format("GET -> /users/{id} RESPONSE: %s", user));

            return new ResponseEntity<User>(user, HttpStatus.OK);
        } catch (ApiException e) {
            log.error(e);
            return new ResponseEntity<String>(e.getMessage(), e.getStatus());
        }
    }
}
