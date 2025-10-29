package ca.ulaval.glo4003.trotti.api.order.controllers;

import ca.ulaval.glo4003.trotti.api.commons.dto.ApiErrorResponse;
import ca.ulaval.glo4003.trotti.api.order.dto.requests.PaymentInfoRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/cart/confirm")
@Tag(name = "Order", description = "Endpoints pour confirmer un order / checkout")
public interface OrderResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Confirmer le panier / placer la commande",
            description = "Confirme le panier et tente de placer l'order en utilisant les PaymentInfo fournis.",
            parameters = {@Parameter(name = "Authorization",
                    description = "Authorization token - JWT", required = true,
                    in = ParameterIn.HEADER,
                    schema = @Schema(type = "string", example = "eyJhbGciOiJIUzI1NiJ9..."))},
            requestBody = @RequestBody(
                    description = "Payment information pour le checkout (card number, card holder, expiration date, cvv)",
                    required = true,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = PaymentInfoRequest.class))),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Order placé avec succès",
                            content = @Content(
                                    schema = @Schema(implementation = ApiErrorResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalide Request",
                            content = @Content(
                                    schema = @Schema(implementation = ApiErrorResponse.class))),
                    @ApiResponse(responseCode = "401",
                            description = "Unauthorized: token manquant ou erroné",
                            content = @Content(
                                    schema = @Schema(implementation = ApiErrorResponse.class))),
                    @ApiResponse(responseCode = "402",
                            description = "Paiement requis: non enregistré", content = @Content(
                                    schema = @Schema(implementation = ApiErrorResponse.class))),})
    Response confirm(
            @Parameter(in = ParameterIn.HEADER, description = "Authorization token - JWT")
            @HeaderParam("Authorization") String tokenRequest,
            PaymentInfoRequest paymentInfoRequest);
}
