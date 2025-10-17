package ca.ulaval.glo4003.trotti.application.trip.dto;

import java.time.Instant;

public record UnlockCodeDto(String code, Instant expirationTime) {
}
