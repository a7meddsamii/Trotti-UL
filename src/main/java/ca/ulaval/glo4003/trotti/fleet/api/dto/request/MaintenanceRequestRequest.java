package ca.ulaval.glo4003.trotti.fleet.api.dto.request;

import jakarta.validation.constraints.NotBlank;

public record MaintenanceRequestRequest(
        @NotBlank String location,
        @NotBlank String message
) {}