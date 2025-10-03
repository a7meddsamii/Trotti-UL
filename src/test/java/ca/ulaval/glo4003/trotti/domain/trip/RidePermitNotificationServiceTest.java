package ca.ulaval.glo4003.trotti.domain.trip;

import ca.ulaval.glo4003.trotti.domain.account.values.Email;
import ca.ulaval.glo4003.trotti.domain.commons.exceptions.EmailSendException;
import ca.ulaval.glo4003.trotti.domain.communication.EmailMessage;
import ca.ulaval.glo4003.trotti.domain.communication.EmailService;
import ca.ulaval.glo4003.trotti.domain.communication.NotificationService;
import java.util.List;
import java.util.stream.IntStream;

import ca.ulaval.glo4003.trotti.fixtures.RidePermitFixture;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class RidePermitNotificationServiceTest {
    private static final Email A_RECIPIENT = Email.from("johndoe@ulaval.ca");
    private EmailService emailService;
    private NotificationService<List<RidePermit>> ridePermitNotificationService;

    @BeforeEach
    void setup() {
        emailService = org.mockito.Mockito.mock(EmailService.class);
        ridePermitNotificationService = new RidePermitNotificationService(emailService);
    }

    @Test
    void givenRidePermitsAndRecipient_whenNotifying_thenSendNotificationForEachRidePermit() {
        List<RidePermit> ridePermits = generateRidePermits();

        ridePermitNotificationService.notify(A_RECIPIENT, ridePermits);

        Mockito.verify(emailService, Mockito.times(ridePermits.size()))
                .send(Mockito.any(EmailMessage.class));
    }

    @Test
    void givenEmailServiceThrowsException_whenNotifying_thenShouldContinueForRemainingPermits() {
        List<RidePermit> ridePermits = generateRidePermits();
        Mockito.doThrow(EmailSendException.class).when(emailService)
                .send(Mockito.any(EmailMessage.class));

        ridePermitNotificationService.notify(A_RECIPIENT, ridePermits);

        Mockito.verify(emailService, Mockito.times(ridePermits.size()))
                .send(Mockito.any(EmailMessage.class));
    }

    private List<RidePermit> generateRidePermits() {
        int numberOfRidePermits = RandomUtils.secure().randomInt(0, 10);
        return IntStream.range(0, numberOfRidePermits)
                .mapToObj(i -> new RidePermitFixture().build()).toList();
    }
}
