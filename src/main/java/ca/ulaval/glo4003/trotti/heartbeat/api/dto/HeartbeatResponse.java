package ca.ulaval.glo4003.trotti.heartbeat.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response indicating the status of the server")
public class HeartbeatResponse {

    @Schema(description = "Le status du serveur", example = "UP")
    private final String status;

    public HeartbeatResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
