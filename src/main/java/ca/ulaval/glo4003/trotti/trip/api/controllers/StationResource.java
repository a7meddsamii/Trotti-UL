package ca.ulaval.glo4003.trotti.trip.api.controllers;

import ca.ulaval.glo4003.trotti.account.api.security.authorization.RequiresPermissions;
import ca.ulaval.glo4003.trotti.account.api.security.identity.AuthenticatedUser;
import ca.ulaval.glo4003.trotti.account.domain.values.Permission;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.trip.api.dto.requests.EndMaintenanceRequest;
import ca.ulaval.glo4003.trotti.trip.api.dto.requests.InitiateTransferRequest;
import ca.ulaval.glo4003.trotti.trip.api.dto.requests.MaintenanceRequestRequest;
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
@Tag(name = "Stations", description = "Endpoints pour les opérations de station")
public interface StationResource {

    @POST
    @Path("/transfers/initiate")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"TECHNICIAN"})
    @RequiresPermissions({Permission.RELOCATE_SCOOTERS})
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
    @RolesAllowed({"TECHNICIAN"})
    @RequiresPermissions({Permission.RELOCATE_SCOOTERS})
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
    @RolesAllowed({"TECHNICIAN"})
    @RequiresPermissions({Permission.START_MAINTENANCE})
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
    @RolesAllowed({"TECHNICIAN"})
    @RequiresPermissions({Permission.END_MAINTENANCE})
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

    @POST
    @Path("/maintenance/request")
    @RolesAllowed({"TECHNICIAN", "EMPLOYEE", "STUDENT"})
    @RequiresPermissions({Permission.REQUEST_MAINTENANCE})
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Demander un service de maintenance",
            description = "Permet à un usager du système de demander une maintenance sur une station",
            responses = {
                    @ApiResponse(responseCode = "204",
                            description = "Demande de service envoyée avec succès"),
                    @ApiResponse(responseCode = "400", description = "Requête invalide"),
                    @ApiResponse(responseCode = "401",
                            description = "Unauthorized: token manquant ou erroné")})
    Response requestMaintenanceService(@Parameter(hidden = true) @AuthenticatedUser Idul userId,
            @Valid MaintenanceRequestRequest request);
}
