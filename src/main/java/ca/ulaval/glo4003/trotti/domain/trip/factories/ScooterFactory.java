package ca.ulaval.glo4003.trotti.domain.trip.factories;

import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.trip.entities.Battery;
import ca.ulaval.glo4003.trotti.domain.trip.entities.Scooter;
import ca.ulaval.glo4003.trotti.domain.trip.values.BatteryLevel;
import ca.ulaval.glo4003.trotti.domain.trip.values.BatteryState;
import ca.ulaval.glo4003.trotti.domain.trip.values.Location;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ScooterFactory {

    private static final BatteryLevel FULL_CHARGE = BatteryLevel.from(BigDecimal.valueOf(100));

    public List<Scooter> createScooters(int count, Location stationLocation) {
        List<Scooter> scooters = new ArrayList<>(count);
        LocalDateTime now = LocalDateTime.now();

        for (int i = 0; i < count; i++) {
            Battery battery = new Battery(FULL_CHARGE, now, BatteryState.CHARGING);
            Scooter scooter = new Scooter(Id.randomId(), battery, stationLocation);
            scooters.add(scooter);
        }

        return scooters;
    }
}
