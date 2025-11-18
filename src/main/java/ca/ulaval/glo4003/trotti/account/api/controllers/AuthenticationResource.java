package ca.ulaval.glo4003.trotti.account.api.controllers;

import ca.ulaval.glo4003.trotti.account.api.dto.LoginRequest;
import ca.ulaval.glo4003.trotti.commons.api.dto.ApiErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/auth")
@Tag(name = "Authentication", description = "Endpoint pour l'authentification des utilisateurs")
public interface AuthenticationResource {

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	@PermitAll
    @Operation(summary = "Se connecter / Login",
            description = "Authentifie l'utilisateur avec email et password.",
            requestBody = @RequestBody(description = "Login request (email et password)",
                    required = true,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = LoginRequest.class))),
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Authentifié avec succès (returns JWT token)",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(type = "string",
                                            example = "eyJhbGciOiJI..."))),
                    @ApiResponse(responseCode = "400", description = "Request invalide",
                            content = @Content),
                    @ApiResponse(responseCode = "401",
                            description = "Unauthorized - Crédentiels invalides",
                            content = @Content(
                                    schema = @Schema(implementation = ApiErrorResponse.class)))})
    Response login(@Valid LoginRequest request);
}
