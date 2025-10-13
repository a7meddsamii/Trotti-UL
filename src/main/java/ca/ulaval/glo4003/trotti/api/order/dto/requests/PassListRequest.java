package ca.ulaval.glo4003.trotti.api.order.dto.requests;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record PassListRequest(
        @NotEmpty(message = "List must not be empty")
        @NotNull(message = "Please provide at least 1 pass") @Valid List<PassRequest> passes
) {
    public record PassRequest(
            @Min(value = 10, message = "Maximum Daily Travel Time must be at least 10 mins") int maximumDailyTravelTime,
            @NotBlank(message = "Session is required") String session,
            @NotBlank(message = "Billing frequency is required") String billingFrequency
    ) {
    }
}
