package ca.ulaval.glo4003.trotti.api.heartbeat.controllers;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/heartbeat")
public interface HeartbeatResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Response heartbeat();
}
