package ca.ulaval.glo4003.trotti.domain.trip.factories;

import ca.ulaval.glo4003.trotti.domain.trip.entities.Scooter;
import ca.ulaval.glo4003.trotti.domain.trip.values.BatteryLevel;
import ca.ulaval.glo4003.trotti.domain.trip.values.Location;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ScooterFactoryTest {

    private static final Location A_STATION_LOCATION = Location.of("PEPS", "Station A");
    private static final int SCOOTER_COUNT = 5;
    private static final BatteryLevel FULL_CHARGE = BatteryLevel.from(BigDecimal.valueOf(100));

    private ScooterFactory scooterFactory;

    @BeforeEach
    void setup() {
        scooterFactory = new ScooterFactory();
    }

    @Test
    void whenCreateScooters_thenReturnsCorrectNumberOfScooters() {
        List<Scooter> scooters = scooterFactory.createScooters(SCOOTER_COUNT, A_STATION_LOCATION);

        Assertions.assertEquals(SCOOTER_COUNT, scooters.size());
    }

    @Test
    void whenCreateScooters_thenAllScootersHaveUniqueIds() {
        List<Scooter> scooters = scooterFactory.createScooters(SCOOTER_COUNT, A_STATION_LOCATION);

        long uniqueIds = scooters.stream().map(Scooter::getId).distinct().count();

        Assertions.assertEquals(SCOOTER_COUNT, uniqueIds);
    }

    @Test
    void whenCreateScooters_thenAllScootersAreAtStationLocation() {
        List<Scooter> scooters = scooterFactory.createScooters(SCOOTER_COUNT, A_STATION_LOCATION);

        boolean allAtStation = scooters.stream()
                .allMatch(scooter -> scooter.getLocation().equals(A_STATION_LOCATION));

        Assertions.assertTrue(allAtStation);
    }

    @Test
    void whenCreateScooters_thenAllScootersHaveFullBattery() {
        List<Scooter> scooters = scooterFactory.createScooters(SCOOTER_COUNT, A_STATION_LOCATION);

        boolean allFullyCharged = scooters.stream()
                .allMatch(scooter -> scooter.getBattery().getBatteryLevel().equals(FULL_CHARGE));

        Assertions.assertTrue(allFullyCharged);
    }

    @Test
    void whenCreateScootersWithZeroCount_thenReturnsEmptyList() {
        List<Scooter> scooters = scooterFactory.createScooters(0, A_STATION_LOCATION);

        Assertions.assertTrue(scooters.isEmpty());
    }
}
