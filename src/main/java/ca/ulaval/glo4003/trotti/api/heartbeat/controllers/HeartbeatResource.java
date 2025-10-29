package ca.ulaval.glo4003.trotti.api.heartbeat.controllers;

import ca.ulaval.glo4003.trotti.api.heartbeat.dto.HeartbeatResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/heartbeat")
@Tag(name = "Heartbeat", description = "Endpoint pour checker si le serveur fonctionne")
public interface HeartbeatResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Service Heartbeat", description = "Checker si le serveur est en marche",
            responses = {@ApiResponse(responseCode = "200", description = "Service is running",
                    content = @Content(
                            schema = @Schema(implementation = HeartbeatResponse.class)))})
    Response heartbeat();
}
