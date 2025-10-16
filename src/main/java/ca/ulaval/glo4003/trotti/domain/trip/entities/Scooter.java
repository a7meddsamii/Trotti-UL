package ca.ulaval.glo4003.trotti.domain.trip.entities;

import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.trip.exceptions.InvalidBatteryValue;
import ca.ulaval.glo4003.trotti.domain.trip.exceptions.InvalidLocation;
import ca.ulaval.glo4003.trotti.domain.trip.values.BatteryState;
import ca.ulaval.glo4003.trotti.domain.trip.values.Location;
import java.time.LocalDateTime;

public class Scooter {
    private final Id id;
    private Battery battery;
    private Location stationLocation;

    public Scooter(Id id, Battery battery, Location stationLocation) {
        this.id = id;
        this.battery = battery;
        this.stationLocation = stationLocation;
    }

    public void dockAt(Location location, LocalDateTime dockingTime) {
        if (!this.stationLocation.isEmpty()) {
            throw new InvalidLocation(
                    "scooter seems to be already docked at " + this.stationLocation);
        }

        this.stationLocation = location;
        this.battery.changeBatteryState(BatteryState.CHARGING, dockingTime);
    }

    public void undock(LocalDateTime undockingTime) {
        if (this.stationLocation.isEmpty()) {
            throw new InvalidLocation("scooter seems to already be undocked");
        }

        if (!battery.hasEnoughCharge()) {
            throw new InvalidBatteryValue("scooter does not have enough battery to be undocked");
        }

        this.stationLocation = Location.empty();
        this.battery.changeBatteryState(BatteryState.DISCHARGING, undockingTime);
    }

    public Location getLocation() {
        return stationLocation;
    }
}
