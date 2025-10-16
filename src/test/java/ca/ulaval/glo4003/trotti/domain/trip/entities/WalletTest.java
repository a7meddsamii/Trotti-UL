package ca.ulaval.glo4003.trotti.domain.trip.entities;

import ca.ulaval.glo4003.trotti.domain.trip.entities.Traveler.Wallet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;

class WalletTest {

    private static final List<RidePermit> NO_RIDE_PERMITS = List.of();

    private RidePermit permit;
    private Wallet wallet;

    @BeforeEach
    void setup() {
        permit = Mockito.mock(RidePermit.class);
        Mockito.when(permit.isActiveFor(Mockito.any(LocalDate.class))).thenReturn(true);

        wallet = new Wallet(NO_RIDE_PERMITS);
    }

    @Test
    void givenNewlyActiveRidePermits_whenUpdateActiveRidePermits_thenAddNewlyWallet() {
        List<RidePermit> ridePermits = List.of(permit);

        wallet.updateActiveRidePermits(ridePermits);

        Assertions.assertEquals(wallet.getRidePermits().size(), ridePermits.size());
    }

    @Test
    void givenExpiredRidePermits_whenUpdateActiveRidePermits_thenRemoveExpiredRidePermits() {
        List<RidePermit> ExpiredPermits = List.of(permit);
        wallet.updateActiveRidePermits(ExpiredPermits);
        List<RidePermit> oldRidePermits =  wallet.getRidePermits();
        Mockito.when(permit.isActiveFor(Mockito.any(LocalDate.class))).thenReturn(false);

        wallet.updateActiveRidePermits(ExpiredPermits);

        Assertions.assertTrue(wallet.getRidePermits().size() < oldRidePermits.size());
    }

    @Test
    void givenTravelerAlreadyHadActiveRidePermit_whenUpdateActiveRidePermits_theReturnsEmptyList(){
        List<RidePermit> activeRidePermits = List.of(permit);
        wallet.updateActiveRidePermits(activeRidePermits);

        List <RidePermit> NewlyPermits = wallet.updateActiveRidePermits(activeRidePermits);

        Assertions.assertTrue(NewlyPermits.isEmpty());
    }

    @Test
    void givenTravelerThatAlreadyHadActiveRidePermitAndNewlyActivatedPermits_whenUpdateActiveRidePermits_thenReturnsNewlyActiveRidePermit() {
        List<RidePermit> activeRidePermits = List.of(permit);
        wallet.updateActiveRidePermits(activeRidePermits);
        RidePermit newActiveRidePermit = Mockito.mock(RidePermit.class);
        List<RidePermit> newlyActiveRidePermits = List.of(newActiveRidePermit);
        Mockito.when(newActiveRidePermit.isActiveFor(Mockito.any(LocalDate.class))).thenReturn(true);

        List<RidePermit> newPermits  = wallet.updateActiveRidePermits(newlyActiveRidePermits);

        Assertions.assertEquals(newlyActiveRidePermits.size(), newPermits.size());
    }

}