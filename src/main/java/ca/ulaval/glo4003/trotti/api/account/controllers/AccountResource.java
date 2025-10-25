package ca.ulaval.glo4003.trotti.api.account.controllers;

import ca.ulaval.glo4003.trotti.api.account.dto.CreateAccountRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/accounts")
@Tag(name = "Accounts", description = "Opérations liées aux comptes utilisateurs")
public interface AccountResource {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Create a new account",
            description = "Creates a new user account with the provided information.",
            requestBody = @RequestBody(description = "Account creation",
                    content = @Content(
                            schema = @Schema(implementation = CreateAccountRequest.class))),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Compte créé avec succès",
                            headers = {
                                    @Header(name = "Location", description = "URI of the created account", schema = @Schema(type = "string", format = "uri"))
                            }),
                    @ApiResponse(responseCode = "400", description = "Request invalide",
                            content = @Content)})
    Response createAccount(@Valid CreateAccountRequest request);
}
