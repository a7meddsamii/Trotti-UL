package ca.ulaval.glo4003.trotti.communication.application;

import ca.ulaval.glo4003.trotti.account.domain.values.Email;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.commons.domain.events.billing.ridepermit.RidePermitActivatedEvent;
import ca.ulaval.glo4003.trotti.commons.domain.events.billing.ridepermit.RidePermitSnapshot;
import ca.ulaval.glo4003.trotti.communication.domain.EmailMessageFactory;
import ca.ulaval.glo4003.trotti.communication.domain.entities.Contact;
import ca.ulaval.glo4003.trotti.communication.domain.services.EmailService;
import ca.ulaval.glo4003.trotti.communication.domain.values.ContactRole;
import ca.ulaval.glo4003.trotti.communication.domain.values.EmailMessage;
import ca.ulaval.glo4003.trotti.communication.infrastructure.repositories.InMemoryContactRepository;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class RidePermitActivationHandlerTest {

    private static final Idul USER_IDUL = Idul.from("jdoe123");
    private static final Email USER_EMAIL = Email.from("john.doe@ulaval.ca");
    private static final String USER_NAME = "John Doe";
    private static final String RIDE_PERMIT_ID = "RP-12345";
    private static final String SESSION = "Fall 2024";
    private static final LocalDate SESSION_START_DATE = LocalDate.of(2024, 9, 1);
    private static final LocalDate SESSION_EXPIRATION_DATE = LocalDate.of(2024, 12, 31);

    private EmailService emailService;
    private EmailMessageFactory emailMessageFactory;
    private InMemoryContactRepository fakeContactRepository;
    private RidePermitActivationHandler handler;
    private EmailMessage emailMessage;

    @BeforeEach
    void setup() {
        emailService = Mockito.mock(EmailService.class);
        emailMessageFactory = Mockito.mock(EmailMessageFactory.class);
        fakeContactRepository = new InMemoryContactRepository();
        handler = new RidePermitActivationHandler(emailService, emailMessageFactory);
        emailMessage = Mockito.mock(EmailMessage.class);

        Contact.setRepository(fakeContactRepository);

        Contact contact = new Contact(USER_IDUL, USER_NAME, USER_EMAIL, ContactRole.STUDENT);
        fakeContactRepository.save(contact);
    }

    @Test
    void givenRidePermitActivatedEvent_whenHandle_thenSendsActivationEmailToAllUsers() {
        RidePermitSnapshot snapshot = new RidePermitSnapshot(USER_IDUL, RIDE_PERMIT_ID, SESSION,
                SESSION_START_DATE, SESSION_EXPIRATION_DATE);
        RidePermitActivatedEvent event = new RidePermitActivatedEvent(List.of(snapshot));
        Mockito.when(emailMessageFactory.createRidePermitActivationMessage(USER_EMAIL, snapshot))
                .thenReturn(emailMessage);

        handler.handle(event);

        Mockito.verify(emailService).send(emailMessage);
    }
}
