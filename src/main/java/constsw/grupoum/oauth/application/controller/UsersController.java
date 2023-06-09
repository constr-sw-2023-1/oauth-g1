package constsw.grupoum.oauth.application.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.Collection;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import constsw.grupoum.oauth.application.exception.ApiException;
import constsw.grupoum.oauth.application.record.RequestNewUser;
import constsw.grupoum.oauth.application.record.RequestUpdateUser;
import constsw.grupoum.oauth.application.record.ResponseError;
import constsw.grupoum.oauth.application.record.ResponseNewUser;
import constsw.grupoum.oauth.application.record.ResponseUser;
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
            @ApiResponse(responseCode = "401", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseError.class))),
            @ApiResponse(responseCode = "403", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseError.class))),
            @ApiResponse(responseCode = "500", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseError.class))) })
    @GetMapping
    public ResponseEntity<?> getAllUsers(@RequestHeader HttpHeaders headers,
            @RequestParam(required = false) Boolean enabled) {
        try {

            log.info("GET -> /users");
            Collection<ResponseUser> users = service.findAll(headersUtils.getValue(headers, HttpHeaders.AUTHORIZATION),
                    enabled);
            log.info(String.format("GET -> /users RESPONSE: %s", users));

            return new ResponseEntity<Collection<ResponseUser>>(users, HttpStatus.OK);
        } catch (ApiException e) {
            log.error(e);
            return new ResponseEntity<ResponseError>(e.getERROR(), e.getSTATUS());
        }
    }

    @Operation(description = "Delete user by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", content = @Content(schema = @Schema(implementation = Void.class))),
            @ApiResponse(responseCode = "401", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseError.class))),
            @ApiResponse(responseCode = "403", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseError.class))),
            @ApiResponse(responseCode = "404", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseError.class))),
            @ApiResponse(responseCode = "500", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseError.class))) })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@RequestHeader HttpHeaders headers, @PathVariable String id) {
        try {

            log.info("DELETE -> /users/{id}");
            service.deleteUser(headersUtils.getValue(headers, HttpHeaders.AUTHORIZATION), id);

            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        } catch (ApiException e) {
            log.error(e);
            return new ResponseEntity<ResponseError>(e.getERROR(), e.getSTATUS());
        }
    }

    @Operation(description = "Get user by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "401", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseError.class))),
            @ApiResponse(responseCode = "403", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseError.class))),
            @ApiResponse(responseCode = "404", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseError.class))),
            @ApiResponse(responseCode = "500", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseError.class))) })
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@RequestHeader HttpHeaders headers, @PathVariable String id) {
        try {

            log.info("GET -> /users/{id}");
            ResponseUser user = service.findById(headersUtils.getValue(headers, HttpHeaders.AUTHORIZATION), id);
            log.info(String.format("GET -> /users/{id} RESPONSE: %s", user));

            return new ResponseEntity<ResponseUser>(user, HttpStatus.OK);
        } catch (ApiException e) {
            log.error(e);
            return new ResponseEntity<ResponseError>(e.getERROR(), e.getSTATUS());
        }
    }

    @Operation(description = "Create new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseNewUser.class))),
            @ApiResponse(responseCode = "401", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseError.class))),
            @ApiResponse(responseCode = "403", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseError.class))),
            @ApiResponse(responseCode = "409", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseError.class))),
            @ApiResponse(responseCode = "500", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseError.class))) })
    @PostMapping
    public ResponseEntity<?> createUser(@RequestHeader HttpHeaders headers, @RequestBody RequestNewUser user) {
        try {
            log.info(String.format("POST -> /users BODY: %s", user));
            ResponseNewUser newUser = service.createUser(headersUtils.getValue(headers, HttpHeaders.AUTHORIZATION),
                    user);
            log.info(String.format("POST -> /users RESPONSE: %s", HttpStatus.CREATED));
            return new ResponseEntity<ResponseNewUser>(newUser, HttpStatus.CREATED);
        } catch (ApiException e) {
            log.error(e);
            return new ResponseEntity<ResponseError>(e.getERROR(), e.getSTATUS());
        }
    }

    @Operation(description = "Update user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Void.class))),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseError.class))),
            @ApiResponse(responseCode = "401", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseError.class))),
            @ApiResponse(responseCode = "403", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseError.class))),
            @ApiResponse(responseCode = "404", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseError.class))),
            @ApiResponse(responseCode = "500", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseError.class))) })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@RequestHeader HttpHeaders headers, @RequestBody RequestUpdateUser user,
            @PathVariable String id) {
        try {
            log.info(String.format("PUT -> /users BODY: %s", user));
            service.updateUser(headersUtils.getValue(headers, HttpHeaders.AUTHORIZATION), id, user);
            log.info(String.format("PUT -> /users RESPONSE: %s", HttpStatus.OK));
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (ApiException e) {
            log.error(e);
            return new ResponseEntity<ResponseError>(e.getERROR(), e.getSTATUS());
        }
    }

    @Operation(description = "Reset password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", content = @Content(schema = @Schema(implementation = Void.class))),
            @ApiResponse(responseCode = "401", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseError.class))),
            @ApiResponse(responseCode = "403", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseError.class))),
            @ApiResponse(responseCode = "404", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseError.class))),
            @ApiResponse(responseCode = "500", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseError.class))) })
    @PatchMapping("/{id}")
    public ResponseEntity<?> newPassword(@RequestHeader HttpHeaders headers, @PathVariable String id,
            @RequestBody String password) {
        try {
            log.info(String.format("PATCH -> /users/%s BODY: password:****", id));
            service.newPassword(headersUtils.getValue(headers, HttpHeaders.AUTHORIZATION),
                    id,
                    password);
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        } catch (ApiException e) {
            log.error(e);
            return new ResponseEntity<ResponseError>(e.getERROR(), e.getSTATUS());
        }
    }

}
