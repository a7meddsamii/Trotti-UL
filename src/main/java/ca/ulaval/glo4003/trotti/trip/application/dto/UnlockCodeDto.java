package ca.ulaval.glo4003.trotti.trip.application.dto;

import java.time.Duration;

public record UnlockCodeDto(String code, Duration expirationTime) {
}
