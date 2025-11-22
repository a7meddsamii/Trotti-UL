package ca.ulaval.glo4003.trotti.trip.api.controllers;

import ca.ulaval.glo4003.trotti.account.api.security.authorization.RequiresPermissions;
import ca.ulaval.glo4003.trotti.account.api.security.identity.AuthenticatedUser;
import ca.ulaval.glo4003.trotti.account.domain.values.Permission;
import ca.ulaval.glo4003.trotti.commons.api.dto.ApiErrorResponse;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.trip.api.dto.requests.EndTripRequest;
import ca.ulaval.glo4003.trotti.trip.api.dto.requests.StartTripRequest;
import ca.ulaval.glo4003.trotti.trip.api.dto.responses.UnlockCodeResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/trips")
@RolesAllowed({"STUDENT", "EMPLOYEE", "TECHNICIAN"})
@RequiresPermissions({Permission.MAKE_TRIP})
@Tag(name = "Trips", description = "Endpoints pour opérations liées aux voyages de voyageur")
public interface TripResource {

    @POST
    @Path("/start")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Démarrer un voyage",
            description = "Permet à un utilisateur de démarrer un nouveau voyage.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Voyage démarré avec succès"),
                    @ApiResponse(responseCode = "400",
                            description = "Bad Request: Requête invalide"),
                    @ApiResponse(responseCode = "401",
                            description = "Unauthorized: token manquant ou erroné"),
                    @ApiResponse(responseCode = "404",
                            description = "Not found: RidePermit non trouvé"),})
    Response startTrip(@Parameter(hidden = true) @AuthenticatedUser Idul userId,
            @Valid StartTripRequest request);

    @POST
    @Path("/end")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Finir un voyage",
            description = "Permet à un utilisateur de finir son voyage en courant.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Voyage fini avec succès"),
                    @ApiResponse(responseCode = "400",
                            description = "Bad Request: Requête invalide"),
                    @ApiResponse(responseCode = "401",
                            description = "Unauthorized: token manquant ou erroné"),
                    @ApiResponse(responseCode = "404",
                            description = "Not found: Aucun trajet en cours")})
    Response endTrip(@Parameter(hidden = true) @AuthenticatedUser Idul userId,
            @Valid EndTripRequest request);
	
	
	@POST
	@Path("/{ridePermitId}/unlock-code")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Demande un unlock code",
			description = "Génère un code pour déverrouiller la trottinette associée à ridePermitId.",
			parameters = {@Parameter(name = "Authorization",
					description = "Authorization token - JWT", required = true,
					in = ParameterIn.HEADER,
					schema = @Schema(type = "string", example = "eyJhbGciOiJIUzI1NiJ9..."))},
			responses = {
					@ApiResponse(responseCode = "200",
							description = "Code de déverrouillage généré avec succès",
							content = @Content(
									schema = @Schema(implementation = UnlockCodeResponse.class))),
					@ApiResponse(responseCode = "401",
							description = "Unauthorized: token manquant ou erroné",
							content = @Content(
									schema = @Schema(implementation = ApiErrorResponse.class))),
					@ApiResponse(responseCode = "404",
							description = "Ride permit non trouvé/non active pour cette session",
							content = @Content(
									schema = @Schema(implementation = UnlockCodeResponse.class)))})
	Response requestUnlockCode(@Parameter(hidden = true) @AuthenticatedUser Idul userId,
							   @PathParam("ridePermitId") String ridePermitId);
}
