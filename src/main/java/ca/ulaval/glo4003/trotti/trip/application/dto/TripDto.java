package ca.ulaval.glo4003.trotti.trip.application.dto;

import ca.ulaval.glo4003.trotti.trip.domain.values.Location;
import ca.ulaval.glo4003.trotti.trip.domain.values.TripId;

import java.time.LocalDateTime;

public record TripDto(
        TripId id,
        Location startLocation,
        Location endLocation,
        LocalDateTime startTime,
        LocalDateTime endTime
) {
}
