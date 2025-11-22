package ca.ulaval.glo4003.trotti.trip.api.controllers;

import ca.ulaval.glo4003.trotti.account.api.security.authorization.RequiresPermissions;
import ca.ulaval.glo4003.trotti.account.api.security.identity.AuthenticatedUser;
import ca.ulaval.glo4003.trotti.account.domain.values.Permission;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.trip.api.dto.requests.EndMaintenanceRequest;
import ca.ulaval.glo4003.trotti.trip.api.dto.requests.InitiateTransferRequest;
import ca.ulaval.glo4003.trotti.trip.api.dto.requests.StartMaintenanceRequest;
import ca.ulaval.glo4003.trotti.trip.api.dto.requests.UnloadScootersRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/stations")
@RolesAllowed({"TECHNICIAN"})
@RequiresPermissions({Permission.START_MAINTENANCE, Permission.END_MAINTENANCE, Permission.RELOCATE_SCOOTERS})
@Tag(name = "Stations", description = "Endpoints pour les opérations de station")
public interface StationResource {

    @POST
    @Path("/transfers/initiate")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Initier un transfert",
            description = "Permet à un technicien d'initier un transfert de scooters",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Transfert initié avec succès"),
                    @ApiResponse(responseCode = "401",
                            description = "Unauthorized: token manquant ou erroné"),
                    @ApiResponse(responseCode = "403",
                            description = "Forbidden: permissions insuffisantes"),
                    @ApiResponse(responseCode = "409",
                            description = "Conflict: station pas en maintenance")})
    Response initiateTransfer(@Parameter(hidden = true) @AuthenticatedUser Idul userId,
            @Valid InitiateTransferRequest request);

    @POST
    @Path("/transfers/{transferId}/unload")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Décharger des scooters",
            description = "Permet à un technicien de décharger des scooters d'un transfert",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Scooters déchargés avec succès"),
                    @ApiResponse(responseCode = "401",
                            description = "Unauthorized: token manquant ou erroné"),
                    @ApiResponse(responseCode = "403",
                            description = "Forbidden: permissions insuffisantes"),
                    @ApiResponse(responseCode = "404",
                            description = "Not found: Transfert non trouvé")})
    Response unloadScooters(@Parameter(hidden = true) @AuthenticatedUser Idul userId,
            @PathParam("transferId") String transferId, @Valid UnloadScootersRequest request);

    @POST
    @Path("/maintenance/start")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Démarrer la maintenance",
            description = "Permet à un technicien de démarrer la maintenance d'une station",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Maintenance démarrée avec succès"),
                    @ApiResponse(responseCode = "401",
                            description = "Unauthorized: token manquant ou erroné"),
                    @ApiResponse(responseCode = "403",
                            description = "Forbidden: permissions insuffisantes"),
                    @ApiResponse(responseCode = "409",
                            description = "Conflict: station déjà en maintenance")})
    Response startMaintenance(@Parameter(hidden = true) @AuthenticatedUser Idul userId,
            @Valid StartMaintenanceRequest request);

    @POST
    @Path("/maintenance/end")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Terminer la maintenance",
            description = "Permet à un technicien de terminer la maintenance d'une station",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Maintenance terminée avec succès"),
                    @ApiResponse(responseCode = "401",
                            description = "Unauthorized: token manquant ou erroné"),
                    @ApiResponse(responseCode = "403",
                            description = "Forbidden: permissions insuffisantes"),
                    @ApiResponse(responseCode = "409",
                            description = "Conflict: station pas en maintenance")})
    Response endMaintenance(@Parameter(hidden = true) @AuthenticatedUser Idul userId,
            @Valid EndMaintenanceRequest request);
}
