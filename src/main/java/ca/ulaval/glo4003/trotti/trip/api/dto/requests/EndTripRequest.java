package ca.ulaval.glo4003.trotti.trip.api.dto.requests;

import jakarta.validation.constraints.NotBlank;

public record EndTripRequest(
        @NotBlank(message = "Location code is required") String location,
        @NotBlank(message = "Slot number is required") String slotNumber
) {
}
