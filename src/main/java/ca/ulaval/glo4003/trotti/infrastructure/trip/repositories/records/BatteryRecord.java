package ca.ulaval.glo4003.trotti.infrastructure.trip.repositories.records;

import ca.ulaval.glo4003.trotti.domain.trip.values.BatteryLevel;
import ca.ulaval.glo4003.trotti.domain.trip.values.BatteryState;
import java.time.LocalDateTime;

public record BatteryRecord(
    BatteryLevel BatteryLevel,
    LocalDateTime lastBatteryUpdate,
    BatteryState currentState
) {}
