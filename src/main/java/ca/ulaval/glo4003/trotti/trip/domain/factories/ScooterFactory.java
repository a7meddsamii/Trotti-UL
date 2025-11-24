package ca.ulaval.glo4003.trotti.trip.domain.factories;

import ca.ulaval.glo4003.trotti.trip.domain.entities.Battery;
import ca.ulaval.glo4003.trotti.trip.domain.entities.Scooter;
import ca.ulaval.glo4003.trotti.trip.domain.values.BatteryLevel;
import ca.ulaval.glo4003.trotti.trip.domain.values.BatteryState;
import ca.ulaval.glo4003.trotti.trip.domain.values.Location;
import ca.ulaval.glo4003.trotti.trip.domain.values.ScooterId;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ScooterFactory {

    private static final BatteryLevel FULL_CHARGE = BatteryLevel.from(BigDecimal.valueOf(100));

    public List<Scooter> create(int count, Location stationLocation) {
        List<Scooter> scooters = new ArrayList<>(count);
        LocalDateTime now = LocalDateTime.now();

        for (int i = 0; i < count; i++) {
            Battery battery = new Battery(FULL_CHARGE, now, BatteryState.CHARGING);
            Scooter scooter = new Scooter(ScooterId.randomId(), battery, stationLocation);
            scooters.add(scooter);
        }

        return scooters;
    }
}
