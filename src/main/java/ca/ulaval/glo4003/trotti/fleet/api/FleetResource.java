package ca.ulaval.glo4003.trotti.fleet.api;

import ca.ulaval.glo4003.trotti.account.api.security.authorization.RequiresPermissions;
import ca.ulaval.glo4003.trotti.account.api.security.identity.AuthenticatedUser;
import ca.ulaval.glo4003.trotti.account.domain.values.Permission;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.fleet.api.dto.request.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/fleet/stations")
@Tag(name = "Fleet Stations", description = "Endpoints pour la gestion de la flotte")
public interface FleetResource {

    @POST
    @Path("/transfers")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("TECHNICIAN")
    @RequiresPermissions(Permission.RELOCATE_SCOOTERS)
    @Operation(summary = "Initier un transfert de scooters")
    @ApiResponse(responseCode = "200", description = "Transfert créé")
    Response startTransfer(@Parameter(hidden = true) @AuthenticatedUser Idul technicianId,
            @Valid StartTransferRequest request);

    @POST
    @Path("/transfers/{transferId}/unload")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("TECHNICIAN")
    @RequiresPermissions(Permission.RELOCATE_SCOOTERS)
    @Operation(summary = "Décharger un transfert")
    @ApiResponse(responseCode = "200", description = "Scooters déposés")
    Response unloadTransfer(@Parameter(hidden = true) @AuthenticatedUser Idul technicianId,
            @PathParam("transferId") String transferId, @Valid UnloadTransferRequest request);

    @POST
    @Path("/maintenance/start")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("TECHNICIAN")
    @RequiresPermissions(Permission.START_MAINTENANCE)
    @Operation(summary = "Démarrer la maintenance")
    Response startMaintenance(@Parameter(hidden = true) @AuthenticatedUser Idul technicianId,
            @Valid StartMaintenanceRequest request);

    @POST
    @Path("/maintenance/end")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("TECHNICIAN")
    @RequiresPermissions(Permission.END_MAINTENANCE)
    @Operation(summary = "Terminer la maintenance")
    Response endMaintenance(@Parameter(hidden = true) @AuthenticatedUser Idul technicianId,
            @Valid EndMaintenanceRequest request);

    @POST
    @Path("/maintenance/request")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"TECHNICIAN", "EMPLOYEE", "STUDENT"})
    @RequiresPermissions(Permission.REQUEST_MAINTENANCE)
    @Operation(summary = "Demander une maintenance")
    Response requestMaintenance(@Parameter(hidden = true) @AuthenticatedUser Idul requesterId,
            @Valid MaintenanceRequestRequest request);

    @GET
    @Path("/{location}/slots/available")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"TECHNICIAN", "EMPLOYEE", "STUDENT"})
    @Operation(summary = "Obtenir les slots disponibles")
    Response getAvailableSlots(@PathParam("location") String location);

    @GET
    @Path("/{location}/slots/occupied")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"TECHNICIAN", "EMPLOYEE", "STUDENT"})
    @Operation(summary = "Obtenir les slots occupés")
    Response getOccupiedSlots(@PathParam("location") String location);
}
