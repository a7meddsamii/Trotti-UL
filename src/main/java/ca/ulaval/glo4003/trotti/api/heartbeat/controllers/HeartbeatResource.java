package ca.ulaval.glo4003.trotti.api.heartbeat.controllers;

import ca.ulaval.glo4003.trotti.api.heartbeat.dto.HeartbeatResponse;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/heartbeat")
public class HeartbeatResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response heartbeat() {
        return Response.ok(new HeartbeatResponse("UP")).build();
    }
}
