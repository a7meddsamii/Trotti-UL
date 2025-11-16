package ca.ulaval.glo4003.trotti.trip.domain.entities;

import ca.ulaval.glo4003.trotti.trip.domain.exceptions.InvalidBatteryValue;
import ca.ulaval.glo4003.trotti.trip.domain.exceptions.InvalidLocationException;
import ca.ulaval.glo4003.trotti.trip.domain.values.BatteryState;
import ca.ulaval.glo4003.trotti.trip.domain.values.Location;
import ca.ulaval.glo4003.trotti.trip.domain.values.ScooterId;
import java.time.LocalDateTime;

public class Scooter {
    private final ScooterId scooterId;
    private final Battery battery;
    private Location location;

    public Scooter(ScooterId scooterId, Battery battery, Location location) {
        this.scooterId = scooterId;
        this.battery = battery;
        this.location = location;
    }

    public void dockAt(Location location, LocalDateTime dockingTime) {
        if (!this.location.isEmpty()) {
            throw new InvalidLocationException(
                    "scooter seems to be already docked at " + this.location);
        }

        this.location = location;
        this.battery.changeBatteryState(BatteryState.CHARGING, dockingTime);
    }

    public void undock(LocalDateTime undockingTime) {
        if (this.location.isEmpty()) {
            throw new InvalidLocationException("scooter seems to already be undocked");
        }

        if (!battery.hasEnoughCharge()) {
            throw new InvalidBatteryValue("scooter does not have enough battery to be undocked");
        }

        this.location = Location.empty();
        this.battery.changeBatteryState(BatteryState.DISCHARGING, undockingTime);
    }

    public Location getLocation() {
        return location;
    }

    public ScooterId getScooterId() {
        return scooterId;
    }

    public Battery getBattery() {
        return battery;
    }

    public void pauseCharging(LocalDateTime pausedTime) {
        battery.pause(pausedTime);
    }

    public void resumeCharging(LocalDateTime resumedTime) {
        battery.resume(resumedTime);
    }
}
