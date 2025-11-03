package ca.ulaval.glo4003.trotti.trip.domain.services;

import ca.ulaval.glo4003.trotti.account.domain.values.Email;
import ca.ulaval.glo4003.trotti.communication.domain.exceptions.EmailSendException;
import ca.ulaval.glo4003.trotti.communication.domain.services.EmailService;
import ca.ulaval.glo4003.trotti.communication.domain.services.NotificationService;
import ca.ulaval.glo4003.trotti.communication.domain.values.EmailMessage;
import ca.ulaval.glo4003.trotti.trip.domain.entities.RidePermit;
import java.util.List;

public class RidePermitNotificationService implements NotificationService<List<RidePermit>> {
    private final EmailService emailService;

    public RidePermitNotificationService(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public void notify(Email recipient, List<RidePermit> newlyActivatedRidePermits) {
        for (RidePermit activatedRidePermit : newlyActivatedRidePermits) {
            try {
                EmailMessage message = EmailMessage.builder().withRecipient(recipient)
                        .withSubject("Ride Permit Activation")
                        .withBody(buildBody(activatedRidePermit)).build();
                emailService.send(message);
            } catch (EmailSendException ignored) {
            }
        }
    }

    private String buildBody(RidePermit ridePermit) {
        return "Your ride permit with ID: " + ridePermit.getId() + " has been activated.\n\n"
                + "Valid for the session: " + ridePermit.getSession() + "\nEffective period: "
                + "\n\t- From: " + ridePermit.getSession().getStartDate() + "\n\t- To: "
                + ridePermit.getSession().getEndDate() + ".\n\nEnjoy your rides!";
    }
}
