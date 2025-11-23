package ca.ulaval.glo4003.trotti.billing.application.ridepermit.dto;

import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.values.RidePermitId;
import java.time.Duration;
import java.time.LocalDateTime;

public record AddTravelTimeDto(RidePermitId ridePermitId, LocalDateTime startDateTime, Duration travelTime) {
}
