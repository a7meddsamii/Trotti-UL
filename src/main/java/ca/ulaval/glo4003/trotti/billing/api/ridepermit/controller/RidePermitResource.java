package ca.ulaval.glo4003.trotti.billing.api.ridepermit.controller;

import ca.ulaval.glo4003.trotti.account.api.security.authorization.RequiresPermissions;
import ca.ulaval.glo4003.trotti.account.api.security.identity.AuthenticatedUser;
import ca.ulaval.glo4003.trotti.account.domain.values.Permission;
import ca.ulaval.glo4003.trotti.billing.api.ridepermit.dto.response.RidePermitResponse;
import ca.ulaval.glo4003.trotti.commons.api.dto.ApiErrorResponse;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/ride-permit")
@RolesAllowed({"TECHNICIAN", "EMPLOYEE", "STUDENT"})
@RequiresPermissions({Permission.MAKE_TRIP})
public interface RidePermitResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get all ride permits for the authenticated user",
            description = "Returns a list of ride permits associated with the authenticated user.",
            parameters = {@Parameter(name = "Authorization",
                    description = "Authorization token - JWT", required = true,
                    in = ParameterIn.HEADER,
                    schema = @Schema(type = "string", example = "eyJhbGciOiJIUzI1NiJ9..."))},
    responses = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval of ride permits",
                    content = @Content(
                            schema = @Schema(implementation = RidePermitResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized access",
                    content = @Content(
                            schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    Response getRidePermits(@Parameter(hidden = true) @AuthenticatedUser Idul userId);

    @GET
    @Path("/{ridePermitId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get a specific ride permit by ID for the authenticated user",
            description = "Returns the ride permit details for the specified ride permit ID associated with the authenticated user.",
            parameters = {
                    @Parameter(name = "ridePermitId", description = "ID of the ride permit to retrieve", required = true,
                            in = ParameterIn.PATH, schema = @Schema(type = "string", example = "RP123456")),
                    @Parameter(name = "Authorization",
                            description = "Authorization token - JWT", required = true,
                            in = ParameterIn.HEADER,
                            schema = @Schema(type = "string", example = "eyJhbGciOiJIUzI1NiJ9..."))
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful retrieval of the ride permit",
                            content = @Content(
                                    schema = @Schema(implementation = RidePermitResponse.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized access",
                            content = @Content(
                                    schema = @Schema(implementation = ApiErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Ride permit not found",
                            content = @Content(
                                    schema = @Schema(implementation = ApiErrorResponse.class)))
            })
    Response getRidePermit(@Parameter(hidden = true) @AuthenticatedUser Idul userId,
            @PathParam("ridePermitId") String ridePermitId);

}
