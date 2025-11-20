package ca.ulaval.glo4003.trotti.trip.api.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record UnloadScootersRequest(
        @NotBlank String destinationStation,
        @NotEmpty List<Integer> destinationSlots
) {}