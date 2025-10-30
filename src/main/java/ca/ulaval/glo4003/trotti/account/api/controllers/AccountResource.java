package ca.ulaval.glo4003.trotti.account.api.controllers;

import ca.ulaval.glo4003.trotti.account.api.dto.CreateAccountRequest;
import ca.ulaval.glo4003.trotti.commons.api.dto.ApiErrorResponse;
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
@Tag(name = "Accounts", description = "Endpoint pour les opérations liées aux comptes utilisateurs")
public interface AccountResource {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Créer un nouveau compte utilisateur",
            description = "Créer un nouveau compte utilisateur avec les infos demandées.",
            requestBody = @RequestBody(description = "Account creation",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = CreateAccountRequest.class))),
            responses = {@ApiResponse(responseCode = "201", description = "Compte créé avec succès",
                    headers = {@Header(name = "Location", description = "URI du compte créé",
                            schema = @Schema(type = "string", format = "uri"))}),
                    @ApiResponse(responseCode = "400", description = "Request invalide",
                            content = @Content(
                                    schema = @Schema(implementation = ApiErrorResponse.class))),
                    @ApiResponse(responseCode = "409",
                            description = "Conflit: Un compte existe déjà avec l'IDUL/le courriel utilisé")})
    Response createAccount(@Valid CreateAccountRequest request);
}
