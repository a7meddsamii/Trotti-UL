package ca.ulaval.glo4003.trotti.trip.api.dto.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record StartTripRequest(
        @NotBlank(message = "Ride permit ID is required") String ridePermitId,
        @NotBlank(message = "Unlock code is required") String unlockCode,
        @NotBlank(message = "Location code is required") String location,
        @Min(value = 0, message = "Slot number must be positive") int slotNumber

) {}