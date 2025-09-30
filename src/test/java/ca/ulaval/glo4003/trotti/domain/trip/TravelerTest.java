package ca.ulaval.glo4003.trotti.domain.trip;


import ca.ulaval.glo4003.trotti.domain.account.values.Email;
import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.communication.EmailMessage;
import ca.ulaval.glo4003.trotti.domain.communication.EmailService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class TravelerTest {

    private static final Idul IDUL = Idul.from("abcd");
    private static final Email EMAIL = Email.from("jhonDoe@ulaval.ca");

    private EmailService emailService;
    private Traveler traveler;
    private RidePermit permit;

    @BeforeEach
    public void setup() {
        emailService =  Mockito.mock(EmailService.class);
        traveler = Mockito.spy( new Traveler(IDUL, EMAIL, emailService));
        permit = Mockito.mock(RidePermit.class);
        Mockito.when(permit.isActiveFor(Mockito.any(LocalDate.class))).thenReturn(true);
    }

    @Test
    void givenNewlyActiveRidePermits_whenUpdateActiveRidePermits_thenAddNewlyActiveRidePermits() {
        List<RidePermit> ridePermits = List.of(permit);
        List<RidePermit>  oldActiveRidePermitsState = traveler.getActiveRidePermits();


        traveler.updateActiveRidePermits(ridePermits);
        List<RidePermit> newActiveRidePermitsState = traveler.getActiveRidePermits();

        Assertions.assertTrue( newActiveRidePermitsState.size() > oldActiveRidePermitsState.size() );
    }

    @Test
    void givenExpiredRidePermits_whenUpdateActiveRidePermits_thenRemoveExpiredPermits() {
        List<RidePermit> ridePermits = List.of(permit);
        traveler.updateActiveRidePermits(ridePermits);
        Mockito.when(permit.isActiveFor(Mockito.any(LocalDate.class))).thenReturn(false);
        List<RidePermit>  oldActiveRidePermitsState = traveler.getActiveRidePermits();

        traveler.updateActiveRidePermits(ridePermits);
        List<RidePermit> newActiveRidePermitsState = traveler.getActiveRidePermits();

        Assertions.assertTrue( newActiveRidePermitsState.size() < oldActiveRidePermitsState.size() );
    }

    @Test
    void givenNewActiveRidePermits_whenUpdateActiveRidePermits_thenNotifyNewActiveRidePermits() {
        List<RidePermit> initialRidePermits = List.of(permit);
        traveler.updateActiveRidePermits(initialRidePermits);
        Mockito.clearInvocations(emailService);
        RidePermit newPermit = Mockito.mock(RidePermit.class);
        Mockito.when(newPermit.isActiveFor(Mockito.any(LocalDate.class))).thenReturn(true);
        List<RidePermit> newRidePermits = List.of(permit,newPermit);

        traveler.updateActiveRidePermits(newRidePermits);

        Mockito.verify(emailService, Mockito.times( 1)).send(Mockito.any(EmailMessage.class));
    }

}