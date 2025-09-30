package ca.ulaval.glo4003.trotti.domain.trip;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.order.Session;
import java.time.LocalDate;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RidePermitTest {

    private static final LocalDate DATE_IN_SESSION = LocalDate.of(2020, 1, 1);
    private static final LocalDate DATE_OUT_OF_SESSION = LocalDate.of(2000, 12, 1);
    private static final Idul IDUL = Idul.from("abcd");
    private static final int LENGTH_OF_RANDOM_STRING = 10;
    private Session session;
    private RidePermit ridePermit;
    private Id ridePermitId;

    @BeforeEach
    void setup() {
        session = Mockito.mock(Session.class);
        ridePermitId = Id.randomId();
        ridePermit = new RidePermit(ridePermitId, IDUL, session);
    }

    @Test
    void givenDateWithinSession_whenIsActive_thenReturnTrue() {
        Mockito.when(session.contains(DATE_IN_SESSION)).thenReturn(true);

        boolean response = ridePermit.isActiveFor(DATE_IN_SESSION);

        Assertions.assertTrue(response);
    }

    @Test
    void givenDateOutOfSessionRange_whenIsActive_thenReturnFalse() {
        Mockito.when(session.contains(DATE_OUT_OF_SESSION)).thenReturn(false);

        boolean response = ridePermit.isActiveFor(DATE_OUT_OF_SESSION);

        Assertions.assertFalse(response);
    }

    @Test
    void givenTwoEqualsRidePermits_whenEquals_thenReturnTrue() {
        RidePermit ridePermit1 = new RidePermit(ridePermitId, IDUL, session);
        RidePermit ridePermit2 = new RidePermit(ridePermitId, IDUL, session);

        boolean response = ridePermit1.equals(ridePermit2);

        Assertions.assertTrue(response);
    }

    @Test
    void givenTwoRidePermitsWithDifferentId_whenEquals_thenReturnFalse() {
        RidePermit ridePermit1 = new RidePermit(ridePermitId, IDUL, session);
        RidePermit ridePermit2 = new RidePermit(Id.randomId(), IDUL, session);

        boolean response = ridePermit1.equals(ridePermit2);

        Assertions.assertFalse(response);
    }

    @Test
    void givenTwoRidePermitsWithDifferentIdul_whenEquals_thenReturnFalse() {
        RidePermit ridePermit1 = new RidePermit(ridePermitId, IDUL, session);
        Idul differentIdul = Idul.from(RandomStringUtils.secure().next(LENGTH_OF_RANDOM_STRING));
        RidePermit ridePermit2 = new RidePermit(ridePermitId, differentIdul, session);

        boolean response = ridePermit1.equals(ridePermit2);

        Assertions.assertFalse(response);
    }

    @Test
    void givenTwoRidePermitsWithDifferentSession_whenEquals_thenReturnFalse() {
        RidePermit ridePermit1 = new RidePermit(ridePermitId, IDUL, session);
        Session differentSession = Mockito.mock(Session.class);
        RidePermit ridePermit2 = new RidePermit(ridePermitId, IDUL, differentSession);

        boolean response = ridePermit1.equals(ridePermit2);

        Assertions.assertFalse(response);
    }

    @Test
    void givenObjectOfDifferentType_whenEquals_thenReturnFalse() {
        boolean response = ridePermit.equals(new Object());

        Assertions.assertFalse(response);
    }
}
