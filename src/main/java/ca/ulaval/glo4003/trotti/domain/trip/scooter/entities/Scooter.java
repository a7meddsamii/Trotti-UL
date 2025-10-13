package ca.ulaval.glo4003.trotti.domain.trip.scooter.entities;

import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.trip.scooter.values.Battery;
import ca.ulaval.glo4003.trotti.domain.trip.scooter.values.BatteryState;
import ca.ulaval.glo4003.trotti.domain.trip.scooter.values.BatteryStrategy;
import java.time.LocalDateTime;
import java.util.Map;

public class Scooter {
    private final Id id;
    private Battery battery;
    private final Map<BatteryState, BatteryStrategy> batteryStrategyMap;
    private LocalDateTime lastBatteryUpdate;

    public Scooter(
            Id id,
            Battery battery,
            Map<BatteryState, BatteryStrategy> batteryStrategyMap,
            LocalDateTime lastBatteryUpdate) {
        this.id = id;
        this.battery = battery;
        this.batteryStrategyMap = batteryStrategyMap;
        this.lastBatteryUpdate = lastBatteryUpdate;
    }

    public void calculateBatteryValue(BatteryState batteryState, LocalDateTime currentTime) {
        this.battery = batteryStrategyMap.get(batteryState).calculateBatteryValue(lastBatteryUpdate,
                currentTime, battery);
        this.lastBatteryUpdate = currentTime;
    }

    public Id getId() {
        return id;
    }
}
