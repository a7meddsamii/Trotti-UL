package ca.ulaval.glo4003.trotti.api.trip.controllers;

import ca.ulaval.glo4003.trotti.api.trip.dto.UnlockCodeResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/unlock-code")
@Tag(name = "Unlock Code", description = "Endpoint de demande d'un code pour déverrouiller une trottinette")
public interface UnlockCodeResource {

    @POST
    @Path("/{ridePermitId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
        summary = "Demande un unlock code",
        description = "Génère un code pour déverrouiller la trottinette associée à ridePermitId.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Code de déverrouillage généré avec succès", content = @Content(schema = @Schema(implementation = UnlockCodeResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized: token manquant ou erroné", content = @Content),
            @ApiResponse(responseCode = "404", description = "Ride permit non trouvé/non active pour cette session", content = @Content)
        }
    )
    Response requestUnlockCode(
            @Parameter(in = ParameterIn.HEADER, description = "Authorization token - JWT")
            @HeaderParam("Authorization") String tokenRequest,
            @PathParam("ridePermitId") String ridePermitId);
}
