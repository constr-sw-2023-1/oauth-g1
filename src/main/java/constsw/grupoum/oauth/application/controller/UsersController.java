package constsw.grupoum.oauth.application.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;

import java.util.Collection;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import constsw.grupoum.oauth.application.exception.ApiException;
import constsw.grupoum.oauth.application.record.RequestNewUser;
import constsw.grupoum.oauth.application.record.ResponseNewUser;
import constsw.grupoum.oauth.application.service.UserService;
import constsw.grupoum.oauth.integration.keycloak.record.User;
import constsw.grupoum.oauth.util.HeadersUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(description = "Get all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = User.class)))),
            @ApiResponse(responseCode = "401", content = @Content(mediaType = TEXT_PLAIN_VALUE, schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", content = @Content(mediaType = TEXT_PLAIN_VALUE, schema = @Schema(implementation = String.class))) })
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

    @Operation(description = "Get user by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "401", content = @Content(mediaType = TEXT_PLAIN_VALUE, schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", content = @Content(mediaType = TEXT_PLAIN_VALUE, schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", content = @Content(mediaType = TEXT_PLAIN_VALUE, schema = @Schema(implementation = String.class))) })
    @GetMapping("/{id}")
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

    @PostMapping
    public ResponseEntity<?> createUser(@RequestHeader HttpHeaders headers, @RequestBody RequestNewUser user) {
        try {
            log.info(String.format("POST -> /users BODY: %s", user));
            ResponseNewUser newUser = service.creatUser(headersUtils.getValue(headers, HttpHeaders.AUTHORIZATION),
                    user);
            log.info(String.format("POST -> /users RESPONSE: %s", HttpStatus.CREATED));
            return new ResponseEntity<ResponseNewUser>(newUser, HttpStatus.CREATED);
        } catch (ApiException e) {
            log.error(e);
            return new ResponseEntity<String>(e.getMessage(), e.getStatus());
        }
    }
}
