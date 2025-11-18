package ca.ulaval.glo4003.trotti.order.api.controllers;

import ca.ulaval.glo4003.trotti.account.domain.values.Permission;
import ca.ulaval.glo4003.trotti.account.infrastructure.security.authorization.RequiresPermissions;
import ca.ulaval.glo4003.trotti.account.infrastructure.security.identity.AuthenticatedUser;
import ca.ulaval.glo4003.trotti.commons.api.dto.ApiErrorResponse;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.order.api.dto.requests.PassListRequest;
import ca.ulaval.glo4003.trotti.order.api.dto.responses.PassListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/cart")
@RolesAllowed("STUDENT")
@RequiresPermissions({Permission.CART_MODIFICATION})
@Tag(name = "Cart", description = "Endpoints for managing the shopping cart")
public interface CartResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get le panier", description = "Retourne le panier courant de l'user",
            parameters = {@Parameter(name = "Authorization",
                    description = "Authorization token - JWT", required = true,
                    in = ParameterIn.HEADER,
                    schema = @Schema(type = "string", example = "eyJhbGciOiJIUzI1NiJ9..."))},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Panier retourné avec succès",
                            content = @Content(
                                    schema = @Schema(implementation = PassListResponse.class))),
                    @ApiResponse(responseCode = "401",
                            description = "Unauthorized: token manquant ou erroné",
                            content = @Content(
                                    schema = @Schema(implementation = ApiErrorResponse.class)))})
    Response getCart(@Parameter(hidden = true) @AuthenticatedUser Idul userId);

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @SecurityRequirement(name = "auth")
    @Operation(summary = "Add des passes au panier",
            description = "Ajoute une liste de passes dans le panier",
            requestBody = @RequestBody(description = "Payload avec les passes à ajouter",
                    required = true,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = PassListRequest.class))),
            parameters = {@Parameter(name = "Authorization",
                    description = "Authorization token - JWT", required = true,
                    in = ParameterIn.HEADER,
                    schema = @Schema(type = "string", example = "eyJhbGciOiJIUzI1NiJ9..."))},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Panier mis à jour",
                            content = @Content(
                                    schema = @Schema(implementation = PassListResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Request invalide",
                            content = @Content),
                    @ApiResponse(responseCode = "401",
                            description = "Unauthorized: token manquant ou erroné",
                            content = @Content(
                                    schema = @Schema(implementation = ApiErrorResponse.class)))})
    Response addToCart(@Parameter(hidden = true) @AuthenticatedUser Idul userId,
            @Valid PassListRequest passListRequest);

    @DELETE
    @Path("/{passId}")
    @SecurityRequirement(name = "auth")
    @Operation(summary = "Enlève un pass du panier",
            description = "Supprime le pass identifié par passId du panier — on l'enlève, c'est simple.",
            parameters = {@Parameter(name = "Authorization",
                    description = "Authorization token - JWT", required = true,
                    in = ParameterIn.HEADER,
                    schema = @Schema(type = "string", example = "eyJhbGciOiJIUzI1NiJ9..."))},
            responses = {
                    @ApiResponse(responseCode = "204", description = "Pass enlevé",
                            content = @Content),
                    @ApiResponse(responseCode = "401",
                            description = "Unauthorized: token manquant ou erroné",
                            content = @Content(
                                    schema = @Schema(implementation = ApiErrorResponse.class)))})
    Response removeFromCart(@Parameter(hidden = true) @AuthenticatedUser Idul userId,
            @Parameter(description = "ID du pass à enlever") @PathParam("passId") String passId);

    @DELETE
    @SecurityRequirement(name = "auth")
    @Operation(summary = "Clear le panier", description = "Vider le panier au complet.",
            parameters = {@Parameter(name = "Authorization",
                    description = "Authorization token - JWT", required = true,
                    in = ParameterIn.HEADER,
                    schema = @Schema(type = "string", example = "eyJhbGciOiJIUzI1NiJ9..."))},
            responses = {@ApiResponse(responseCode = "204", description = "Panier vidé"),
                    @ApiResponse(responseCode = "401",
                            description = "Unauthorized: token manquant ou erroné",
                            content = @Content(
                                    schema = @Schema(implementation = ApiErrorResponse.class)))})
    Response clearCart(@Parameter(hidden = true) @AuthenticatedUser Idul userId);
}
