package ca.ulaval.glo4003.trotti.account.api.controllers;

import ca.ulaval.glo4003.trotti.account.api.dto.CreateAccountRequest;
import ca.ulaval.glo4003.trotti.account.api.security.authorization.RequiresPermissions;
import ca.ulaval.glo4003.trotti.account.api.security.identity.AuthenticatedUser;
import ca.ulaval.glo4003.trotti.account.domain.values.Permission;
import ca.ulaval.glo4003.trotti.commons.api.dto.ApiErrorResponse;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/accounts")
@Tag(name = "Accounts", description = "Endpoint pour les opérations liées aux comptes utilisateurs")
public interface AccountResource {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
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

    @POST
    @Path("/company")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("ADMIN")
    @RequiresPermissions({Permission.CREATE_ADMIN, Permission.CREATE_EMPLOYEE})
    @Operation(summary = "Créer un compte administrateur ou technicien (géré par l'entreprise)",
            description = "Permet à un compte admin déjà authentifié de créer un compte ADMIN ou TECHNICIAN. "
                    + "Le rôle est déterminé par le champ 'role' du request body. "
                    + "Les permissions sont validées à partir des permissions du créateur.",
            requestBody = @RequestBody(description = "Informations du compte à créer",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = CreateAccountRequest.class))),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Compte créé avec succès",
                            headers = @Header(name = "Location", description = "URI du compte créé",
                                    schema = @Schema(type = "string", format = "uri"))),
                    @ApiResponse(responseCode = "400", description = "Request invalide",
                            content = @Content(
                                    schema = @Schema(implementation = ApiErrorResponse.class))),
                    @ApiResponse(responseCode = "401",
                            description = "Unauthorized: token manquant ou erroné",
                            content = @Content(
                                    schema = @Schema(implementation = ApiErrorResponse.class))),
                    @ApiResponse(responseCode = "409",
                            description = "Conflit: Un compte existe déjà avec l'IDUL/le courriel utilisé")})
    Response createAdminManagedAccount(@Parameter(hidden = true) @AuthenticatedUser Idul userId,
            @Valid CreateAccountRequest request);
}
