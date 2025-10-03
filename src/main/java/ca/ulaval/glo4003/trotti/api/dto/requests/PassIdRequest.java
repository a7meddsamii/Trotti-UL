package ca.ulaval.glo4003.trotti.api.dto.requests;

import jakarta.validation.constraints.NotBlank;

public record PassIdRequest (
        @NotBlank(message = "Pass ID is required") String passId
) {
}
