package ca.ulaval.glo4003.trotti.trip.api.controllers;

import ca.ulaval.glo4003.trotti.trip.api.dto.requests.InitiateTransferRequest;
import ca.ulaval.glo4003.trotti.trip.api.dto.requests.UnloadScootersRequest;
import ca.ulaval.glo4003.trotti.trip.api.dto.responses.TransferResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/transfers")
@Tag(name = "Transfers", description = "Endpoints pour les transferts de scooters")
public interface TransferResource {

    @POST
    @Path("/initiate")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Initier un transfert",
            description = "Permet à un technicien d'initier un transfert de scooters",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Transfert initié avec succès"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized: token manquant ou erroné"),
                    @ApiResponse(responseCode = "403", description = "Forbidden: permissions insuffisantes")
            })
    Response initiateTransfer(@HeaderParam("Authorization") String tokenHeader,
                             @Valid InitiateTransferRequest request);

    @POST
    @Path("/{transferId}/unload")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Décharger des scooters",
            description = "Permet à un technicien de décharger des scooters d'un transfert",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Scooters déchargés avec succès"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized: token manquant ou erroné"),
                    @ApiResponse(responseCode = "403", description = "Forbidden: permissions insuffisantes"),
                    @ApiResponse(responseCode = "404", description = "Not found: Transfert non trouvé")
            })
    Response unloadScooters(@HeaderParam("Authorization") String tokenHeader,
                           @PathParam("transferId") String transferId,
                           @Valid UnloadScootersRequest request);
}