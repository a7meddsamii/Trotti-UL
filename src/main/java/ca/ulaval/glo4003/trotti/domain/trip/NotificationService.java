package ca.ulaval.glo4003.trotti.domain.trip;

import ca.ulaval.glo4003.trotti.domain.account.values.Email;
import ca.ulaval.glo4003.trotti.domain.commons.exceptions.EmailSendException;
import ca.ulaval.glo4003.trotti.domain.communication.EmailMessage;
import ca.ulaval.glo4003.trotti.domain.communication.EmailService;
import java.util.List;

public class NotificationService {
    private final EmailService emailService;

    public NotificationService(EmailService emailService) {
        this.emailService = emailService;
    }

    public void notify(Email recipient, List<RidePermit> newlyActivatedRidePermits) {
        for (RidePermit activatedRidePermit : newlyActivatedRidePermits) {
            try {
                EmailMessage message = EmailMessage.builder().withRecipient(recipient)
                        .withSubject("Newly Activated Ride Permit")
                        .withBody(activatedRidePermit + " has been activated.").build();
                emailService.send(message);
            } catch (EmailSendException ignored) {
            }
        }
    }
}
