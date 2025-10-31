package ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.records;

import ca.ulaval.glo4003.trotti.trip.domain.values.BatteryLevel;
import ca.ulaval.glo4003.trotti.trip.domain.values.BatteryState;
import java.time.LocalDateTime;

public record BatteryRecord(
    BatteryLevel BatteryLevel,
    LocalDateTime lastBatteryUpdate,
    BatteryState currentState
) {}
