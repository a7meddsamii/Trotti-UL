package ca.ulaval.glo4003.trotti.communication.application;

import ca.ulaval.glo4003.trotti.account.domain.values.Email;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.commons.domain.events.billing.order.OrderPlacedEvent;
import ca.ulaval.glo4003.trotti.communication.domain.EmailMessageFactory;
import ca.ulaval.glo4003.trotti.communication.domain.entities.Contact;
import ca.ulaval.glo4003.trotti.communication.domain.services.EmailService;
import ca.ulaval.glo4003.trotti.communication.domain.values.ContactRole;
import ca.ulaval.glo4003.trotti.communication.domain.values.EmailMessage;
import ca.ulaval.glo4003.trotti.communication.infrastructure.repositories.InMemoryContactRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CommunicationOrderPlacedHandlerTest {

    private static final Idul CUSTOMER_IDUL = Idul.from("jdoe123");
    private static final String ORDER_ID = "ORDER-12345";
    private static final Email CUSTOMER_EMAIL = Email.from("john.doe@ulaval.ca");
    private static final String CUSTOMER_NAME = "John Doe";

    private EmailService emailService;
    private EmailMessageFactory emailMessageFactory;
    private InMemoryContactRepository fakeContactRepository;
    private CommunicationOrderPlacedHandler handler;
    private EmailMessage emailMessage;

    @BeforeEach
    void setup() {
        emailService = Mockito.mock(EmailService.class);
        emailMessageFactory = Mockito.mock(EmailMessageFactory.class);
        fakeContactRepository = new InMemoryContactRepository();
        handler = new CommunicationOrderPlacedHandler(emailService, emailMessageFactory);
        emailMessage = Mockito.mock(EmailMessage.class);

        Contact.setRepository(fakeContactRepository);

        Contact contact =
                new Contact(CUSTOMER_IDUL, CUSTOMER_NAME, CUSTOMER_EMAIL, ContactRole.STUDENT);
        fakeContactRepository.save(contact);
    }

    @Test
    void givenOrderPlacedEvent_whenHandle_thenSendsOrderConfirmationEmail() {
        OrderPlacedEvent event = new OrderPlacedEvent(CUSTOMER_IDUL, ORDER_ID, List.of());
        Mockito.when(emailMessageFactory.createOrderConfirmationMessage(CUSTOMER_EMAIL, ORDER_ID))
                .thenReturn(emailMessage);

        handler.handle(event);

        Mockito.verify(emailService).send(emailMessage);
    }
}
