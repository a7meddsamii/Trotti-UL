package ca.ulaval.glo4003.trotti.fleet.api.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record UnloadTransferRequest(
        @NotBlank String destinationStation,
        @NotEmpty List<@Min(0) Integer> destinationSlots
) {}
