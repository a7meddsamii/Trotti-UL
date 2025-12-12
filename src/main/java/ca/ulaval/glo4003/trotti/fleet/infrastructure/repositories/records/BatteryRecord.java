package ca.ulaval.glo4003.trotti.fleet.infrastructure.repositories.records;

import ca.ulaval.glo4003.trotti.fleet.domain.values.BatteryLevel;
import ca.ulaval.glo4003.trotti.fleet.domain.values.BatteryState;
import java.time.LocalDateTime;

public record BatteryRecord(
    BatteryLevel BatteryLevel,
    LocalDateTime lastBatteryUpdate,
    BatteryState currentState
) {}
