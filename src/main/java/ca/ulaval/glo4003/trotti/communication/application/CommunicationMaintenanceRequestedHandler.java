package ca.ulaval.glo4003.trotti.communication.application;

import ca.ulaval.glo4003.trotti.commons.domain.events.trip.MaintenanceRequestedEvent;
import ca.ulaval.glo4003.trotti.communication.domain.EmailMessageFactory;
import ca.ulaval.glo4003.trotti.communication.domain.entities.Contact;
import ca.ulaval.glo4003.trotti.communication.domain.services.EmailService;
import ca.ulaval.glo4003.trotti.communication.domain.values.ContactRole;
import ca.ulaval.glo4003.trotti.communication.domain.values.EmailMessage;
import java.util.List;

public class CommunicationMaintenanceRequestedHandler {

    private final EmailService emailService;
    private final EmailMessageFactory emailMessageFactory;

    public CommunicationMaintenanceRequestedHandler(EmailService emailService, EmailMessageFactory emailMessageFactory) {
        this.emailService = emailService;
        this.emailMessageFactory = emailMessageFactory;
    }

    public void handle(MaintenanceRequestedEvent event) {
        List<Contact> technicians = Contact.findAllByRole(ContactRole.TECHNICIAN);

        technicians.forEach(technician -> {
            EmailMessage message = emailMessageFactory.createMaintenanceMessage(technician.getEmail(),
                    event.getLocation(), event.getMessage());

            emailService.send(message);
        });
    }
}
