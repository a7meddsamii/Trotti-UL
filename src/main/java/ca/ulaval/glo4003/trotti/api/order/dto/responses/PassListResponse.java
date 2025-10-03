package ca.ulaval.glo4003.trotti.api.order.dto.responses;

import java.util.List;

public record PassListResponse(
        List<PassListResponse.PassResponse> passes,
        String total
) {
    public record PassResponse(
            String id,
            String maximumDailyTravelTime,
            String session,
            String billingFrequency,
            String price
    ) {
    }
}
