package ca.ulaval.glo4003.trotti.api.heartbeat.dto;

public class HeartbeatResponse {

    private final String status;

    public HeartbeatResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
