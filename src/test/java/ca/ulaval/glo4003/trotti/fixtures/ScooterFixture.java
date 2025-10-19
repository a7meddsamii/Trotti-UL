package ca.ulaval.glo4003.trotti.fixtures;

import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.trip.entities.Battery;
import ca.ulaval.glo4003.trotti.domain.trip.entities.Scooter;
import ca.ulaval.glo4003.trotti.domain.trip.values.BatteryLevel;
import ca.ulaval.glo4003.trotti.domain.trip.values.BatteryState;
import ca.ulaval.glo4003.trotti.domain.trip.values.Location;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ScooterFixture {

    public static final Id A_SCOOTER_ID = Id.randomId();
    public static final BatteryLevel A_BATTERY_LEVEL = BatteryLevel.from(BigDecimal.valueOf(80));
    public static final LocalDateTime A_LAST_UPDATE = LocalDateTime.of(2024, 1, 1, 12, 0);
    public static final BatteryState A_BATTERY_STATE = BatteryState.CHARGING;
    public static final Location A_LOCATION = Location.of("PEPS", "Station A");

    private Id id = A_SCOOTER_ID;
    private BatteryLevel batteryLevel = A_BATTERY_LEVEL;
    private LocalDateTime lastUpdate = A_LAST_UPDATE;
    private BatteryState batteryState = A_BATTERY_STATE;
    private Location location = A_LOCATION;

    public ScooterFixture withId(Id id) {
        this.id = id;
        return this;
    }

    public ScooterFixture withBatteryLevel(BatteryLevel batteryLevel) {
        this.batteryLevel = batteryLevel;
        return this;
    }

    public ScooterFixture withLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
        return this;
    }

    public ScooterFixture withBatteryState(BatteryState batteryState) {
        this.batteryState = batteryState;
        return this;
    }

    public ScooterFixture withLocation(Location location) {
        this.location = location;
        return this;
    }

    public Scooter build() {
        Battery battery = new Battery(batteryLevel, lastUpdate, batteryState);
        return new Scooter(id, battery, location);
    }
}
