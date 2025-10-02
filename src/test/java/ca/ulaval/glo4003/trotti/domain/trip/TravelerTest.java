package ca.ulaval.glo4003.trotti.domain.trip;

import ca.ulaval.glo4003.trotti.domain.account.values.Email;
import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TravelerTest {

    private static final Idul IDUL = Idul.from("abcd");
    private static final Email EMAIL = Email.from("jhonDoe@ulaval.ca");
    private static final List<RidePermit> NO_RIDE_PERMITS = List.of();

    private Traveler traveler;
    private RidePermit permit;

    @BeforeEach
    void setup() {
        traveler = Mockito.spy(new Traveler(IDUL, EMAIL, NO_RIDE_PERMITS));
        permit = Mockito.mock(RidePermit.class);
        Mockito.when(permit.isActiveFor(Mockito.any(LocalDate.class))).thenReturn(true);
    }

    @Test
    void givenNewlyActiveRidePermits_whenUpdateActiveRidePermits_thenAddNewlyActiveRidePermits() {
        List<RidePermit> ridePermits = List.of(permit);
        List<RidePermit> oldActiveRidePermitsState = traveler.getRidePermits();

        traveler.updateActiveRidePermits(ridePermits);
        List<RidePermit> newActiveRidePermitsState = traveler.getRidePermits();

        Assertions.assertTrue(newActiveRidePermitsState.size() > oldActiveRidePermitsState.size());
    }

    @Test
    void givenExpiredRidePermits_whenUpdateActiveRidePermits_thenRemoveExpiredPermits() {
        List<RidePermit> ridePermits = List.of(permit);
        traveler.updateActiveRidePermits(ridePermits);
        Mockito.when(permit.isActiveFor(Mockito.any(LocalDate.class))).thenReturn(false);
        List<RidePermit> oldActiveRidePermitsState = traveler.getRidePermits();

        traveler.updateActiveRidePermits(ridePermits);
        List<RidePermit> newActiveRidePermitsState = traveler.getRidePermits();

        Assertions.assertTrue(newActiveRidePermitsState.size() < oldActiveRidePermitsState.size());
    }

    @Test
    void givenTravelerThatAlreadyHadActiveRidePermitAndNewlyActivatedPermits_whenUpdateActiveRidePermits_thenReturnNewlyActivatedRidePermits() {
        traveler.updateActiveRidePermits(List.of(permit));
        List<RidePermit> oldActiveRidePermitsState = traveler.getRidePermits();
        List<RidePermit> newActiveRidePermits = List.of(mockActiveRidePermit());
        List<RidePermit> ridePermits = Stream
                .concat(oldActiveRidePermitsState.stream(), newActiveRidePermits.stream()).toList();

        List<RidePermit> newActiveRidePermitsDetected =
                traveler.updateActiveRidePermits(ridePermits);

        Assertions.assertEquals(newActiveRidePermits, newActiveRidePermitsDetected);
    }

    private RidePermit mockActiveRidePermit() {
        RidePermit ridePermit = Mockito.mock(RidePermit.class);
        Mockito.when(ridePermit.isActiveFor(Mockito.any(LocalDate.class))).thenReturn(true);
        return ridePermit;
    }
}
