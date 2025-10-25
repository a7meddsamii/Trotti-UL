package ca.ulaval.glo4003.trotti.api.trip.controllers;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/unlock-code")
public interface UnlockCodeResource {

    @POST
    @Path("/{ridePermitId}")
    @Produces(MediaType.APPLICATION_JSON)
    Response requestUnlockCode(@HeaderParam("Authorization") String tokenRequest,
                                      @PathParam("ridePermitId") String ridePermitId);
}
