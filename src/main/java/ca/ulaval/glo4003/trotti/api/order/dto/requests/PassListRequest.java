package ca.ulaval.glo4003.trotti.api.order.dto.requests;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record PassListRequest(
        @NotEmpty(message = "List must not be empty")
        @Valid List<PassRequest> passes
) {
    public record PassRequest(
            @Min(1) int maximumDailyTravelTime,
            @NotBlank(message = "Session is required") String session,
            @NotBlank(message = "Billing frequency is required") String billingFrequency
    ) {
    }
}
