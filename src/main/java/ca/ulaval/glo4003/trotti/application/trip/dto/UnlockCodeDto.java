package ca.ulaval.glo4003.trotti.application.trip.dto;

import java.time.Duration;

public record UnlockCodeDto(String code, Duration expirationTime) {
}
