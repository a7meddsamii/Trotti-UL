package ca.ulaval.glo4003.trotti.api.trip.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "UnlockCodeResponse")
public record UnlockCodeResponse(
        @Schema(example = "Le code de déverrouillage est généré avec succès.")
        String message) {
}
