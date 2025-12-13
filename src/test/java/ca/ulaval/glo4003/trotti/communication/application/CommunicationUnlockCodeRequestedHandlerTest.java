package ca.ulaval.glo4003.trotti.communication.application;

import ca.ulaval.glo4003.trotti.commons.domain.events.trip.UnlockCodeRequestedEvent;
import ca.ulaval.glo4003.trotti.communication.domain.EmailMessageFactory;
import ca.ulaval.glo4003.trotti.communication.domain.entities.Contact;
import ca.ulaval.glo4003.trotti.communication.domain.services.EmailService;
import ca.ulaval.glo4003.trotti.communication.domain.values.ContactRole;
import ca.ulaval.glo4003.trotti.communication.domain.values.EmailMessage;
import ca.ulaval.glo4003.trotti.communication.infrastructure.repositories.InMemoryContactRepository;
import ca.ulaval.glo4003.trotti.account.domain.values.Email;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CommunicationUnlockCodeRequestedHandlerTest {

    private static final Idul USER_IDUL = Idul.from("jdoe123");
    private static final String RIDE_PERMIT_ID = "RID-12345";
    private static final LocalDateTime EXPIRATION_DATE = LocalDateTime.now().plusDays(1);
    private static final String UNLOCK_CODE = "ABC123";
    private static final String USER_NAME = "John Doe";
    private static final Email USER_EMAIL = Email.from("john.doe@ulaval.ca");

    private EmailService emailService;
    private EmailMessageFactory emailMessageFactory;
    private InMemoryContactRepository fakeContactRepository;
    private CommunicationUnlockCodeRequestedHandler handler;
    private EmailMessage emailMessage;

    @BeforeEach
    void setup() {
        emailService = Mockito.mock(EmailService.class);
        emailMessageFactory = Mockito.mock(EmailMessageFactory.class);
        fakeContactRepository = new InMemoryContactRepository();
        handler = new CommunicationUnlockCodeRequestedHandler(emailService, emailMessageFactory);
        emailMessage = Mockito.mock(EmailMessage.class);
        
        Contact.setRepository(fakeContactRepository);
        
        Contact contact = new Contact(USER_IDUL, USER_NAME, USER_EMAIL, ContactRole.STUDENT);
        fakeContactRepository.save(contact);
    }

    @Test
    void givenUnlockCodeRequestedEvent_whenHandle_thenSendsUnlockCodeEmail() {
        UnlockCodeRequestedEvent event = new UnlockCodeRequestedEvent(USER_IDUL, RIDE_PERMIT_ID, UNLOCK_CODE, EXPIRATION_DATE);
        Mockito.when(emailMessageFactory.createUnlockCodeMessage(USER_EMAIL, USER_NAME, UNLOCK_CODE))
                .thenReturn(emailMessage);

        handler.handle(event);

        Mockito.verify(emailService).send(emailMessage);
    }
}