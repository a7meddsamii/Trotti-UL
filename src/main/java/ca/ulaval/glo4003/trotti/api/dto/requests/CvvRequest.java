package ca.ulaval.glo4003.trotti.api.dto.requests;

import jakarta.validation.constraints.NotEmpty;

public record CvvRequest(
        @NotEmpty(message = "CVV is required") String cvv
) {
}
