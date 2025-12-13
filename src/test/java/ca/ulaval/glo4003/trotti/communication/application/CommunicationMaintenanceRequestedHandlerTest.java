package ca.ulaval.glo4003.trotti.communication.application;

import ca.ulaval.glo4003.trotti.account.domain.values.Email;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.commons.domain.events.trip.MaintenanceRequestedEvent;
import ca.ulaval.glo4003.trotti.communication.domain.EmailMessageFactory;
import ca.ulaval.glo4003.trotti.communication.domain.entities.Contact;
import ca.ulaval.glo4003.trotti.communication.domain.services.EmailService;
import ca.ulaval.glo4003.trotti.communication.domain.values.ContactRole;
import ca.ulaval.glo4003.trotti.communication.domain.values.EmailMessage;
import ca.ulaval.glo4003.trotti.communication.infrastructure.repositories.InMemoryContactRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CommunicationMaintenanceRequestedHandlerTest {

    private static final Idul MAINTENANCE_IDUL = Idul.from("stud123");
    private static final String MAINTENANCE_LOCATION = "VACHON Building";
    private static final String MAINTENANCE_MESSAGE = "Scooter needs battery replacement";
    private static final Email TECHNICIAN_EMAIL = Email.from("tech@ulaval.ca");
    private static final String TECHNICIAN_NAME = "Tech Support";
    private static final Idul TECHNICIAN_IDUL = Idul.from("tech123");

    private EmailService emailService;
    private EmailMessageFactory emailMessageFactory;
    private InMemoryContactRepository fakeContactRepository;
    private CommunicationMaintenanceRequestedHandler handler;
    private EmailMessage emailMessage;

    @BeforeEach
    void setup() {
        emailService = Mockito.mock(EmailService.class);
        emailMessageFactory = Mockito.mock(EmailMessageFactory.class);
        fakeContactRepository = new InMemoryContactRepository();
        handler = new CommunicationMaintenanceRequestedHandler(emailService, emailMessageFactory);
        emailMessage = Mockito.mock(EmailMessage.class);

        Contact.setRepository(fakeContactRepository);

        Contact technician = new Contact(TECHNICIAN_IDUL, TECHNICIAN_NAME, TECHNICIAN_EMAIL,
                ContactRole.TECHNICIAN);
        fakeContactRepository.save(technician);
    }

    @Test
    void givenMaintenanceRequestedEvent_whenHandle_thenSendsEmailToAllTechnicians() {
        MaintenanceRequestedEvent event = new MaintenanceRequestedEvent(MAINTENANCE_IDUL,
                MAINTENANCE_LOCATION, MAINTENANCE_MESSAGE);
        Mockito.when(emailMessageFactory.createMaintenanceMessage(TECHNICIAN_EMAIL,
                MAINTENANCE_LOCATION, MAINTENANCE_MESSAGE)).thenReturn(emailMessage);

        handler.handle(event);

        Mockito.verify(emailService).send(emailMessage);
    }
}
