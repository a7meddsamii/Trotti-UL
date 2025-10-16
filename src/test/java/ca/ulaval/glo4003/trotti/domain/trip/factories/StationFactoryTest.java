package ca.ulaval.glo4003.trotti.domain.trip.factories;

import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.trip.exceptions.InvalidStation;
import ca.ulaval.glo4003.trotti.domain.trip.values.Location;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

public class StationFactoryTest {

    private static final Location A_LOCATION = Mockito.mock(Location.class);
    private static final Id A_SCOOTER_ID = Id.randomId();
    private static final List<Id> A_SCOOTER_ID_LIST = new ArrayList<>();
    private static final int A_VALID_CAPACITY = 2;
    private static final int ZERO_CAPACITY = 0;
    private static final int NEGATIVE_CAPACITY = -1;
    private StationFactory factory;

    @BeforeEach
    void setup() {
        factory = new StationFactory();
        A_SCOOTER_ID_LIST.add(A_SCOOTER_ID);
    }

    @Test
    void givenStationFactory_whenCreateStationWithValidCapacity_thenDoesNotThrow() {
        Executable creation = () -> factory.create(A_LOCATION, A_SCOOTER_ID_LIST, A_VALID_CAPACITY);

        Assertions.assertDoesNotThrow(creation);
    }

    @Test
    void whenCreateStationWithZeroCapacity_thenThrowInvalidStationException() {
        Executable creation = () -> factory.create(A_LOCATION, A_SCOOTER_ID_LIST, ZERO_CAPACITY);

        Assertions.assertThrows(InvalidStation.class, creation);
    }

    @Test
    void whenCreateStationWithNegativeCapacity_thenThrowInvalidStationException() {
        Executable creation =
                () -> factory.create(A_LOCATION, A_SCOOTER_ID_LIST, NEGATIVE_CAPACITY);

        Assertions.assertThrows(InvalidStation.class, creation);
    }
}
