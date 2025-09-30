package ca.ulaval.glo4003.trotti.domain.trip;


import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.order.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;

@ExtendWith(MockitoExtension.class)
class PassTest {

    private static final LocalDate DATE_IN_SESSION = LocalDate.of(2020, 1, 1);
    private static final LocalDate DATE_OUT_OF_SESSION = LocalDate.of(2000, 12, 1);
    private Session session;
    private Pass pass;

    @BeforeEach
    public void setup() {
        session = Mockito.mock(Session.class);
        pass = new Pass(Id.randomId(), Idul.from("abdc"),session);
    }




    @Test
    public void givenDateWithinSession_whenIsActive_thenReturnTrue() {
        Mockito.when(session.contains(DATE_IN_SESSION)).thenReturn(true);

        boolean response = pass.isActiveFor(DATE_IN_SESSION);

        Assertions.assertTrue(response);
    }


    @Test
    public void givenDateOutOfSessionRange_whenIsActive_thenReturnFalse() {
        Mockito.when(session.contains(DATE_OUT_OF_SESSION)).thenReturn(false);

        boolean response = pass.isActiveFor(DATE_OUT_OF_SESSION);

        Assertions.assertFalse(response);
    }


}