package ca.ulaval.glo4003.trotti.domain.trip.entities.traveler;

import ca.ulaval.glo4003.trotti.domain.account.values.Email;
import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.trip.entities.Trip;
import ca.ulaval.glo4003.trotti.domain.trip.exceptions.TravelerException;
import ca.ulaval.glo4003.trotti.domain.trip.values.RidePermitId;
import ca.ulaval.glo4003.trotti.domain.trip.values.ScooterId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

import java.time.LocalDateTime;




class TravelerTest {

    private static final Idul AN_IDUL = Idul.from("anIdul");
    private static final Email AN_EMAIL = Email.from("anEmail@ulaval.ca");
    private static final RidePermitId A_RIDE_PERMIT_ID = RidePermitId.randomId();
    private static final ScooterId A_SCOOTER_ID = ScooterId.randomId();
    private static final LocalDateTime A_TIME = LocalDateTime.now();
    private static final LocalDateTime A_LATER_TIME = LocalDateTime.now().plusMinutes(1);

    private RidePermitWallet  ridePermitWallet;
    private Traveler traveler;

    @BeforeEach
    void setup() {
        ridePermitWallet = Mockito.mock(RidePermitWallet.class);
        traveler = new Traveler(AN_IDUL, AN_EMAIL, ridePermitWallet);
        Mockito.when(ridePermitWallet.hasRidePermit(A_RIDE_PERMIT_ID)).thenReturn(true);
    }


    @Test
    void givenStartTimeExistingRidePassIdAndScooterId_whenStartTraveling_thenOngoingTripIsNotNull() {
        traveler.startTraveling(A_TIME,A_RIDE_PERMIT_ID,A_SCOOTER_ID);

        Assertions.assertTrue(traveler.getOngoingTrip().isPresent());
    }

    @Test
    void givenStartTimeNoExistingRidePassIdAndScooterId_whenStartTraveling_thenThrowTravelerException() {
        Mockito.when(ridePermitWallet.hasRidePermit(A_RIDE_PERMIT_ID)).thenReturn(false);

        Executable startingTravelingWithWrongRiderPass =
                () -> traveler.startTraveling(A_TIME, A_RIDE_PERMIT_ID, A_SCOOTER_ID);

        Assertions.assertThrows(TravelerException.class, startingTravelingWithWrongRiderPass);
    }

    @Test
    void givenTravelerHasOngoingTrip_whenStartTraveling_thenThrowTravelerException() {
        traveler.startTraveling(A_TIME,A_RIDE_PERMIT_ID,A_SCOOTER_ID);

        Executable startingTravelingWhenAlreadyTraveling =
                () -> traveler.startTraveling(A_TIME, A_RIDE_PERMIT_ID, A_SCOOTER_ID);

        Assertions.assertThrows(TravelerException.class, startingTravelingWhenAlreadyTraveling);
    }

    @Test
    void givenEndTimeWithOngoingTrip_whenStopTraveling_thenReturnTripWithEndTime() {
        traveler.startTraveling(A_TIME,A_RIDE_PERMIT_ID,A_SCOOTER_ID);

        Trip finishedTrip =  traveler.stopTraveling(A_LATER_TIME);

        Assertions.assertEquals(A_LATER_TIME, finishedTrip.getEndTime());
    }

    @Test
    void givenEndTimeWithOngoingTrip_whenStopTraveling_thenOngoingTripIsNull() {
        traveler.startTraveling(A_TIME,A_RIDE_PERMIT_ID,A_SCOOTER_ID);

        traveler.stopTraveling(A_LATER_TIME);

        Assertions.assertTrue(traveler.getOngoingTrip().isEmpty());
    }

    @Test
    void givenEndTimeWithoutOngoingTrip_whenStopTraveling_thenThrowTravelerException() {
        Executable endingTripWithoutOngoingTrip =
                () -> traveler.stopTraveling(A_TIME);

        Assertions.assertThrows(TravelerException.class, endingTripWithoutOngoingTrip);
    }



}