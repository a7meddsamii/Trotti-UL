package ca.ulaval.glo4003.trotti.trip.api.dto.requests;

import jakarta.ws.rs.QueryParam;
import java.time.LocalDate;

public class TripQueryRequest {

    @QueryParam("startDate")
    public String startDate;

    @QueryParam("endDate")
    public String endDate;

    public TripQueryRequest() {}

    public LocalDate getStartDate() {
        try {
            return LocalDate.parse(this.startDate);
        } catch (Exception e) {
            return null;
        }
    }

    public LocalDate getEndDate() {
        try {
            return LocalDate.parse(this.endDate);
        } catch (Exception e) {
            return null;
        }
    }
}
