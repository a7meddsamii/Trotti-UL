package ca.ulaval.glo4003.trotti.api.dto.responses;

import java.util.List;

public record PassListResponse(
        List<PassListResponse.PassResponse> passes,
        String totalCost
) {
    public record PassResponse(
            String id,
            String maximumDailyTravelTime,
            String session,
            String billingFrequency,
            String cost
    ) {
    }
}
