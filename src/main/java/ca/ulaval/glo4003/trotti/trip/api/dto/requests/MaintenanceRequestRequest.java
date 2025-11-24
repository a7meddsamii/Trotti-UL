package ca.ulaval.glo4003.trotti.trip.api.dto.requests;

import jakarta.validation.constraints.NotBlank;

public record MaintenanceRequestRequest(
        @NotBlank String location,
        @NotBlank String message
) {}