package ca.ulaval.glo4003.trotti.communication.application;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.commons.domain.events.account.AccountCreatedEvent;
import ca.ulaval.glo4003.trotti.communication.domain.entities.Contact;
import ca.ulaval.glo4003.trotti.communication.infrastructure.repositories.InMemoryContactRepository;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CommunicationAccountCreatedHandlerTest {

    private static final String ACCOUNT_NAME = "John Doe";
    private static final String ACCOUNT_EMAIL = "john.doe@ulaval.ca";
    private static final String ACCOUNT_ROLE = "student";
    private static final Idul ACCOUNT_IDUL = Idul.from("jdoe123");

    private InMemoryContactRepository fakeContactRepository;
    private CommunicationAccountCreatedHandler handler;

    @BeforeEach
    void setup() {
        fakeContactRepository = new InMemoryContactRepository();
        handler = new CommunicationAccountCreatedHandler();

        Contact.setRepository(fakeContactRepository);
    }

    @Test
    void givenAccountCreatedEvent_whenHandle_thenCreatesContactAndSaves() {
        AccountCreatedEvent event = new AccountCreatedEvent(ACCOUNT_IDUL, ACCOUNT_NAME,
                ACCOUNT_EMAIL, ACCOUNT_ROLE, List.of());

        handler.handle(event);
        Contact savedContact = fakeContactRepository.findByIdul(ACCOUNT_IDUL);

        Assertions.assertNotNull(savedContact);
        Assertions.assertEquals(ACCOUNT_IDUL, savedContact.getIdul());
    }
}
