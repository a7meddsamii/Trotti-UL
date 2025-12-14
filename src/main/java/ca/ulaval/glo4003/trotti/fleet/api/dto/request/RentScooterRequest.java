package ca.ulaval.glo4003.trotti.fleet.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RentScooterRequest(
        @NotBlank String location,
        @NotNull Integer slotNumber
) {}
