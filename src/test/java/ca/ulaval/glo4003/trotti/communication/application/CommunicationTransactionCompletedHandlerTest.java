package ca.ulaval.glo4003.trotti.communication.application;

import ca.ulaval.glo4003.trotti.account.domain.values.Email;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.commons.domain.events.billing.payment.TransactionCompletedEvent;
import ca.ulaval.glo4003.trotti.communication.domain.EmailMessageFactory;
import ca.ulaval.glo4003.trotti.communication.domain.entities.Contact;
import ca.ulaval.glo4003.trotti.communication.domain.services.EmailService;
import ca.ulaval.glo4003.trotti.communication.domain.values.ContactRole;
import ca.ulaval.glo4003.trotti.communication.domain.values.EmailMessage;
import ca.ulaval.glo4003.trotti.communication.infrastructure.repositories.InMemoryContactRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CommunicationTransactionCompletedHandlerTest {

    private static final Idul CUSTOMER_IDUL = Idul.from("jdoe123");
    private static final String TRANSACTION_ID = "TXN-67890";
    private static final boolean TRANSACTION_SUCCESS = true;
    private static final String TRANSACTION_STATUS = "success";
    private static final String TRANSACTION_DESCRIPTION = "Monthly pass payment";
    private static final Email CUSTOMER_EMAIL = Email.from("john.doe@ulaval.ca");
    private static final String CUSTOMER_NAME = "John Doe";

    private EmailService emailService;
    private EmailMessageFactory emailMessageFactory;
    private InMemoryContactRepository fakeContactRepository;
    private CommunicationTransactionCompletedHandler handler;
    private EmailMessage emailMessage;

    @BeforeEach
    void setup() {
        emailService = Mockito.mock(EmailService.class);
        emailMessageFactory = Mockito.mock(EmailMessageFactory.class);
        fakeContactRepository = new InMemoryContactRepository();
        handler = new CommunicationTransactionCompletedHandler(emailService, emailMessageFactory);
        emailMessage = Mockito.mock(EmailMessage.class);

        Contact.setRepository(fakeContactRepository);

        Contact contact =
                new Contact(CUSTOMER_IDUL, CUSTOMER_NAME, CUSTOMER_EMAIL, ContactRole.STUDENT);
        fakeContactRepository.save(contact);
    }

    @Test
    void givenTransactionCompletedEvent_whenHandle_thenSendsTransactionCompletedEmail() {
        TransactionCompletedEvent event = new TransactionCompletedEvent(CUSTOMER_IDUL,
                TRANSACTION_ID, TRANSACTION_SUCCESS, TRANSACTION_DESCRIPTION);
        Mockito.when(emailMessageFactory.createTransactionCompletedMessage(CUSTOMER_EMAIL,
                TRANSACTION_ID, TRANSACTION_STATUS, TRANSACTION_DESCRIPTION))
                .thenReturn(emailMessage);

        handler.handle(event);

        Mockito.verify(emailService).send(emailMessage);
    }
}
