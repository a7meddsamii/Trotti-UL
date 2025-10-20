package ca.ulaval.glo4003.trotti.domain.trip.entities.traveler;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.trip.entities.Trip;
import ca.ulaval.glo4003.trotti.domain.trip.exceptions.TripBookException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

class TripWalletTest {

    private static final Id AN_ID = Id.randomId();
    private static final LocalDateTime START_DATE = LocalDateTime.now();
    private static final LocalDateTime END_DATE = START_DATE.plusMinutes(1);
    private static final Idul AN_IDUL = Idul.from("abc");

    private TripWallet tripWallet;
    private Trip trip;
    private List<Trip> trips;

    @BeforeEach
    public void setup() {
        trips = new ArrayList<>();
        trip = Mockito.mock(Trip.class);
        tripWallet = new TripWallet(trips);
    }

    @Test
    void givenTrip_whenAddTrip_thenAddTrip() {
        tripWallet.add(trip);

        Assertions.assertEquals(trip, tripWallet.getTrips().getFirst());
    }

    @Test
    void givenNullTrip_whenAddTrip_thenThrowsTripBookException() {
        Executable nullTripAdd = () -> tripWallet.add(null);

        Assertions.assertThrows(TripBookException.class, nullTripAdd);
    }

    @Test
    void givenTripAlreadyBooked_whenAddTrip_thenThrowsTripBookException() {
        tripWallet.add(trip);

        Executable existingTripAdd = () -> tripWallet.add(trip);

        Assertions.assertThrows(TripBookException.class, existingTripAdd);
    }

    @Test
    void givenTripIdAndEndDate_whenEndTrip_ThenRemovesTrip() {
        tripWallet.add(trip);
        Mockito.when(trip.getId()).thenReturn(AN_ID);

        tripWallet.endTrip(AN_ID, END_DATE);

        Assertions.assertTrue(tripWallet.getTrips().isEmpty());
    }

    @Test
    void givenTripIdAndEndDate_whenEndTrip_ThenReturnsTripWithEndDate() {
        Trip startTrip = new Trip(AN_ID, START_DATE, AN_ID, AN_IDUL, AN_ID);
        tripWallet.add(startTrip);

        Trip endTrip = tripWallet.endTrip(AN_ID, END_DATE);

        Assertions.assertEquals(startTrip.end(END_DATE).getEndTime(), endTrip.getEndTime());
    }

    @Test
    void givenTripIdNotPresentAndStartDate_whenEndTrip_ThenThrowsTripBookException() {
        Executable NoTripEnd = () -> tripWallet.endTrip(AN_ID, START_DATE);

        Assertions.assertThrows(TripBookException.class, NoTripEnd);
    }

}
