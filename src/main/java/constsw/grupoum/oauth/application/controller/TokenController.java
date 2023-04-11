package constsw.grupoum.oauth.application.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import constsw.grupoum.oauth.application.exception.ApiException;
import constsw.grupoum.oauth.application.record.RequestLogin;
import constsw.grupoum.oauth.application.record.RequestRefreshToken;
import constsw.grupoum.oauth.application.record.ResponseError;
import constsw.grupoum.oauth.application.service.TokenService;
import constsw.grupoum.oauth.integration.keycloak.record.Token;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@RestController
public class TokenController {

    private final TokenService tokenService;

    @Operation(description = "Login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = Token.class))),
            @ApiResponse(responseCode = "401", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseError.class))),
            @ApiResponse(responseCode = "500", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseError.class))) })
    @PostMapping(path = "/login", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> login(@ModelAttribute RequestLogin requestLogin) {
        try {
            log.info(String.format("POST -> /login Request Body: Username: %s Password: *****",
                    requestLogin.username()));
            Token token = tokenService.retrieveTokenWithCredentials(requestLogin);
            log.info(String.format("POST -> /login RESPONSE: %s", token));

            return new ResponseEntity<Token>(token, HttpStatus.OK);
        } catch (ApiException e) {
            log.error(e);
            return new ResponseEntity<ResponseError>(e.getERROR(), e.getSTATUS());
        }
    }

    @Operation(description = "Refresh token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = Token.class))),
            @ApiResponse(responseCode = "500", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseError.class))) })
    @PostMapping(path = "/token", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> token(@ModelAttribute RequestRefreshToken requestRefreshToken) {
        try {
            log.info(String.format("POST -> /token Request Body: Refresh Token: %s",
                    requestRefreshToken.refreshToken()));
            Token token = tokenService.retrieveTokenWithRefreshToken(requestRefreshToken);
            log.info(String.format("POST -> /token RESPONSE: %s", token));

            return new ResponseEntity<Token>(token, HttpStatus.OK);
        } catch (ApiException e) {
            log.error(e);
            return new ResponseEntity<ResponseError>(e.getERROR(), e.getSTATUS());
        }
    }
}
