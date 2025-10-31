package ca.ulaval.glo4003.trotti.heartbeat.api.controllers;

import ca.ulaval.glo4003.trotti.heartbeat.api.dto.HeartbeatResponse;
import jakarta.ws.rs.core.Response;

public class HeartbeatController implements HeartbeatResource {

    @Override
    public Response heartbeat() {
        return Response.ok(new HeartbeatResponse("UP")).build();
    }
}
