package ca.ulaval.glo4003.trotti.domain.trip;

import ca.ulaval.glo4003.trotti.domain.account.values.Email;
import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.communication.EmailMessage;
import ca.ulaval.glo4003.trotti.domain.communication.EmailService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Traveler {
    private final EmailService emailService;
    private final Idul idul;
    private final Email email;
    private List<RidePermit> activeRidePermits;

    public Traveler(Idul idul, Email email, EmailService emailService) {
        this.idul = idul;
        this.email = email;
        this.emailService = emailService;
        this.activeRidePermits = new ArrayList<>();
    }

    public void updateActiveRidePermits(List<RidePermit> ridePermits) {
        List<RidePermit> activeRidePermits = ridePermits.stream()
                .filter(ridePermit -> ridePermit.isActiveFor(LocalDate.now())).toList();
        List<RidePermit> newlyActiveRidePermits = ridePermits.stream()
                .filter(ridePermit -> !this.activeRidePermits.contains(ridePermit)).toList();
        notify(newlyActiveRidePermits);
        this.activeRidePermits = activeRidePermits;
    }

    private void notify(List<RidePermit> ridePermits) {
        for (RidePermit ridePermit : ridePermits) {
            EmailMessage emailMessage = EmailMessage.builder().withRecipient(this.email)
                    .withSubject("Newly activated pass")
                    .withBody(ridePermit + "has been activated.").build();
            this.emailService.send(emailMessage);
        }
    }

    public List<RidePermit> getActiveRidePermits() {
        return activeRidePermits;
    }

    public Idul getIdul() {
        return idul;
    }

}
