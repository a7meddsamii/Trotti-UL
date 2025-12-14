package ca.ulaval.glo4003.trotti.trip.fixtures;

import ca.ulaval.glo4003.trotti.fleet.domain.entities.Battery;
import ca.ulaval.glo4003.trotti.fleet.domain.entities.Scooter;
import ca.ulaval.glo4003.trotti.fleet.domain.values.BatteryLevel;
import ca.ulaval.glo4003.trotti.fleet.domain.values.BatteryState;
import ca.ulaval.glo4003.trotti.fleet.domain.values.Location;
import ca.ulaval.glo4003.trotti.fleet.domain.values.ScooterId;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @deprecated This class will be reimplemented
 */
public class ScooterFixture {

    public static final ScooterId SCOOTER_ID = ScooterId.randomId();
    public static final BatteryLevel BATTERY_LEVEL = BatteryLevel.from(BigDecimal.valueOf(80));
    public static final LocalDateTime LAST_UPDATE = LocalDateTime.of(2024, 1, 1, 12, 0);
    public static final BatteryState BATTERY_STATE = BatteryState.CHARGING;
    public static final Location LOCATION = Location.of("PEPS", "Station A");

    private ScooterId scooterId = SCOOTER_ID;
    private BatteryLevel batteryLevel = BATTERY_LEVEL;
    private LocalDateTime lastUpdate = LAST_UPDATE;
    private BatteryState batteryState = BATTERY_STATE;
    private Location location = LOCATION;

    public ScooterFixture withId(ScooterId scooterId) {
        this.scooterId = scooterId;
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
        return new Scooter(scooterId, battery, location);
    }
}
