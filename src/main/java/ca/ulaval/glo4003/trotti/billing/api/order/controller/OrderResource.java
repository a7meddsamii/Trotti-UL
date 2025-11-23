package ca.ulaval.glo4003.trotti.billing.api.order.controller;

import ca.ulaval.glo4003.trotti.account.api.security.authorization.RequiresPermissions;
import ca.ulaval.glo4003.trotti.account.api.security.identity.AuthenticatedUser;
import ca.ulaval.glo4003.trotti.account.domain.values.Permission;
import ca.ulaval.glo4003.trotti.billing.api.order.dto.request.ItemRequest;
import ca.ulaval.glo4003.trotti.billing.api.order.dto.response.ItemListResponse;
import ca.ulaval.glo4003.trotti.commons.api.dto.ApiErrorResponse;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
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

@Path("/order")
@RolesAllowed({"STUDENT"})
@RequiresPermissions({Permission.CART_MODIFICATION, Permission.ORDER_CONFIRM})
@Tag(name = "Order", description = "Endpoints for managing the user's order")
public interface OrderResource {
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@SecurityRequirement(name = "auth")
	@Operation(summary = "Ajoute un item à la commande",
			description = "Ajoute un item à la commande",
			requestBody = @RequestBody(description = "L'item à ajouter",
					required = true,
					content = @Content(mediaType = MediaType.APPLICATION_JSON,
							schema = @Schema(implementation = ItemRequest.class))),
			parameters = {@Parameter(name = "Authorization",
					description = "Authorization token - JWT", required = true,
					in = ParameterIn.HEADER,
					schema = @Schema(type = "string", example = "eyJhbGciOiJIUzI1NiJ9..."))},
			responses = {
					@ApiResponse(responseCode = "200", description = "Commande mise à jour",
							content = @Content(
									schema = @Schema(implementation = ItemListResponse.class))),
					@ApiResponse(responseCode = "400", description = "Request invalide",
							content = @Content),
					@ApiResponse(responseCode = "401",
							description = "Unauthorized: token manquant ou erroné",
							content = @Content(
									schema = @Schema(implementation = ApiErrorResponse.class)))})
	Response addItem(@Parameter(hidden = true) @AuthenticatedUser Idul userId,
					 @Valid ItemRequest request);
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Retourne la commande", description = "Retourne la commande courante de l'utilisateur",
			parameters = {@Parameter(name = "Authorization",
					description = "Authorization token - JWT", required = true,
					in = ParameterIn.HEADER,
					schema = @Schema(type = "string", example = "eyJhbGciOiJIUzI1NiJ9..."))},
			responses = {
					@ApiResponse(responseCode = "200", description = "Commande retournée avec succès",
							content = @Content(
									schema = @Schema(implementation = ItemListResponse.class))),
					@ApiResponse(responseCode = "401",
							description = "Unauthorized: token manquant ou erroné",
							content = @Content(
									schema = @Schema(implementation = ApiErrorResponse.class)))})
	Response getOngoingOrder(@Parameter(hidden = true) @AuthenticatedUser Idul userId);
	
	@DELETE
	@Path("/{itemId}")
	@SecurityRequirement(name = "auth")
	@Operation(summary = "Supprime un item à la commande",
			description = "Supprime l'item identifié par itemId de la commande — on l'enlève, c'est simple.",
			parameters = {@Parameter(name = "Authorization",
					description = "Authorization token - JWT", required = true,
					in = ParameterIn.HEADER,
					schema = @Schema(type = "string", example = "eyJhbGciOiJIUzI1NiJ9..."))},
			responses = {
					@ApiResponse(responseCode = "204", description = "Item enlevé",
							content = @Content),
					@ApiResponse(responseCode = "401",
							description = "Unauthorized: token manquant ou erroné",
							content = @Content(
									schema = @Schema(implementation = ApiErrorResponse.class)))})
	Response removeItem(@Parameter(hidden = true) @AuthenticatedUser Idul userId,
							@Parameter(description = "ID de l'item à enlever") @PathParam("itemId") String itemId);
	
	@DELETE
	@SecurityRequirement(name = "auth")
	@Operation(summary = "Vide la commande", description = "Vide la commande au complet.",
			parameters = {@Parameter(name = "Authorization",
					description = "Authorization token - JWT", required = true,
					in = ParameterIn.HEADER,
					schema = @Schema(type = "string", example = "eyJhbGciOiJIUzI1NiJ9..."))},
			responses = {@ApiResponse(responseCode = "204", description = "Commande vidée"),
					@ApiResponse(responseCode = "401",
							description = "Unauthorized: token manquant ou erroné",
							content = @Content(
									schema = @Schema(implementation = ApiErrorResponse.class)))})
	Response removeAllItems(@Parameter(hidden = true) @AuthenticatedUser Idul userId);
}
