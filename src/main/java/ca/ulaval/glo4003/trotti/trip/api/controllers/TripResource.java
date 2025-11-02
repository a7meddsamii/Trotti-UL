package ca.ulaval.glo4003.trotti.trip.api.controllers;

import ca.ulaval.glo4003.trotti.trip.api.dto.requests.EndTripRequest;
import ca.ulaval.glo4003.trotti.trip.api.dto.requests.StartTripRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/trips")
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
    Response startTrip(@HeaderParam("Authorization") String tokenHeader,
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
    Response endTrip(@HeaderParam("Authorization") String tokenHeader,
            @Valid EndTripRequest request);
}
