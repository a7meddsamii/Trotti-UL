package ca.ulaval.glo4003.trotti.api.trip.controllers;

import ca.ulaval.glo4003.trotti.api.commons.dto.ApiErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/traveler")
@Tag(name = "Traveler", description = "Endpoint pour les opérations liées au voyageur")
public interface TravelerResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Obtenir les permis de passes currently actifs",
            description = "Retourne la liste des permis de passees associés au voyageur authentifié, actifs pour la session actuelle.",
            parameters = {@Parameter(name = "Authorization",
                    description = "Authorization token - JWT", required = true,
                    in = ParameterIn.HEADER,
                    schema = @Schema(type = "string", example = "eyJhbGciOiJIUzI1NiJ9..."))},
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Permis de passes retournés avec succès",
                            content = @Content(schema = @Schema(implementation = List.class))),
                    @ApiResponse(responseCode = "401",
                            description = "Unauthorized: token manquant ou erroné",
                            content = @Content(
                                    schema = @Schema(implementation = ApiErrorResponse.class)))})
    Response getRidePermits(@HeaderParam("Authorization") String tokenRequest);
}
