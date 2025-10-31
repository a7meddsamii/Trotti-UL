package ca.ulaval.glo4003.trotti.trip.domain.entities.traveler;

import ca.ulaval.glo4003.trotti.trip.domain.entities.RidePermit;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class RidePermitWalletTest {

    private static final List<RidePermit> NO_RIDE_PERMITS = List.of();

    private RidePermit permit;
    private RidePermitWallet ridePermitWallet;

    @BeforeEach
    void setup() {
        permit = Mockito.mock(RidePermit.class);
        Mockito.when(permit.isActiveFor(Mockito.any(LocalDate.class))).thenReturn(true);

        ridePermitWallet = new RidePermitWallet(NO_RIDE_PERMITS);
    }

    @Test
    void givenNewlyActiveRidePermits_whenUpdateActiveRidePermits_thenAddNewlyWallet() {
        List<RidePermit> ridePermits = List.of(permit);

        ridePermitWallet.updateActiveRidePermits(ridePermits);

        Assertions.assertEquals(ridePermitWallet.getRidePermits().size(), ridePermits.size());
    }

    @Test
    void givenExpiredRidePermits_whenUpdateActiveRidePermits_thenRemoveExpiredRidePermits() {
        List<RidePermit> ExpiredPermits = List.of(permit);
        ridePermitWallet.updateActiveRidePermits(ExpiredPermits);
        List<RidePermit> oldRidePermits = ridePermitWallet.getRidePermits();
        Mockito.when(permit.isActiveFor(Mockito.any(LocalDate.class))).thenReturn(false);

        ridePermitWallet.updateActiveRidePermits(ExpiredPermits);

        Assertions.assertTrue(ridePermitWallet.getRidePermits().size() < oldRidePermits.size());
    }

    @Test
    void givenTravelerAlreadyHadActiveRidePermit_whenUpdateActiveRidePermits_theReturnsEmptyList() {
        List<RidePermit> activeRidePermits = List.of(permit);
        ridePermitWallet.updateActiveRidePermits(activeRidePermits);

        List<RidePermit> NewlyPermits = ridePermitWallet.updateActiveRidePermits(activeRidePermits);

        Assertions.assertTrue(NewlyPermits.isEmpty());
    }

    @Test
    void givenTravelerThatAlreadyHadActiveRidePermitAndNewlyActivatedPermits_whenUpdateActiveRidePermits_thenReturnsNewlyActiveRidePermit() {
        List<RidePermit> activeRidePermits = List.of(permit);
        ridePermitWallet.updateActiveRidePermits(activeRidePermits);
        RidePermit newActiveRidePermit = Mockito.mock(RidePermit.class);
        List<RidePermit> newlyActiveRidePermits = List.of(newActiveRidePermit);
        Mockito.when(newActiveRidePermit.isActiveFor(Mockito.any(LocalDate.class)))
                .thenReturn(true);

        List<RidePermit> newPermits =
                ridePermitWallet.updateActiveRidePermits(newlyActiveRidePermits);

        Assertions.assertEquals(newlyActiveRidePermits.size(), newPermits.size());
    }

}
