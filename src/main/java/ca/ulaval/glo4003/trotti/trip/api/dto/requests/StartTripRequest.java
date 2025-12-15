package ca.ulaval.glo4003.trotti.trip.api.dto.requests;

import jakarta.validation.constraints.NotBlank;

public record StartTripRequest(
        @NotBlank(message = "Ride permit ID is required") String ridePermitId,
        @NotBlank(message = "Unlock code is required") String unlockCode,
        @NotBlank(message = "Location code is required") String location,
        @NotBlank(message = "Slot number is required") int slotNumber

) {}