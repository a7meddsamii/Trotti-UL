package ca.ulaval.glo4003.trotti.billing.api.ridepermit.controller;

import ca.ulaval.glo4003.trotti.account.api.security.authorization.RequiresPermissions;
import ca.ulaval.glo4003.trotti.account.api.security.identity.AuthenticatedUser;
import ca.ulaval.glo4003.trotti.account.domain.values.Permission;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import io.swagger.v3.oas.annotations.Parameter;
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
	Response getRidePermits(@Parameter(hidden = true) @AuthenticatedUser Idul userId);
	
	@GET
	@Path("/{ridePermitId}")
	@Produces(MediaType.APPLICATION_JSON)
	Response getRidePermit(@Parameter(hidden = true) @AuthenticatedUser Idul userId, @PathParam("ridePermitId") String ridePermitId);

}
