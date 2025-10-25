package ca.ulaval.glo4003.trotti.api.trip.controllers;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/traveler")
public interface TravelerResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Response getRidePermits(@HeaderParam("Authorization") String tokenRequest);
}
