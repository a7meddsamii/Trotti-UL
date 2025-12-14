package ca.ulaval.glo4003.trotti.fleet.domain.factories;

import ca.ulaval.glo4003.trotti.fleet.domain.entities.Scooter;
import ca.ulaval.glo4003.trotti.fleet.domain.values.BatteryLevel;
import ca.ulaval.glo4003.trotti.fleet.domain.values.Location;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ScooterFactoryTest {

    private static final Location STATION_LOCATION = Location.of("PEPS", "Station A");
    private static final int SCOOTER_COUNT = 5;
    private static final BatteryLevel FULL_CHARGE = BatteryLevel.from(BigDecimal.valueOf(100));

    private ScooterFactory scooterFactory;

    @BeforeEach
    void setup() {
        scooterFactory = new ScooterFactory();
    }

    @Test
    void whenCreateScooters_thenReturnsCorrectNumberOf() {
        List<Scooter> scooters = scooterFactory.create(SCOOTER_COUNT, STATION_LOCATION);

        Assertions.assertEquals(SCOOTER_COUNT, scooters.size());
    }

    @Test
    void whenCreateScooters_thenAllHaveUniqueIds() {
        List<Scooter> scooters = scooterFactory.create(SCOOTER_COUNT, STATION_LOCATION);

        long uniqueIds = scooters.stream().map(Scooter::getScooterId).distinct().count();

        Assertions.assertEquals(SCOOTER_COUNT, uniqueIds);
    }

    @Test
    void whenCreateScooters_thenAllAreAtStationLocation() {
        List<Scooter> scooters = scooterFactory.create(SCOOTER_COUNT, STATION_LOCATION);

        boolean allAtStation = scooters.stream()
                .allMatch(scooter -> scooter.getLocation().equals(STATION_LOCATION));

        Assertions.assertTrue(allAtStation);
    }

    @Test
    void whenCreateScooters_thenAllHaveFullBattery() {
        List<Scooter> scooters = scooterFactory.create(SCOOTER_COUNT, STATION_LOCATION);

        boolean allFullyCharged = scooters.stream()
                .allMatch(scooter -> scooter.getBattery().getBatteryLevel().equals(FULL_CHARGE));

        Assertions.assertTrue(allFullyCharged);
    }

    @Test
    void whenCreateWithZeroCount_thenReturnsEmptyList() {
        List<Scooter> scooters = scooterFactory.create(0, STATION_LOCATION);

        Assertions.assertTrue(scooters.isEmpty());
    }
}
