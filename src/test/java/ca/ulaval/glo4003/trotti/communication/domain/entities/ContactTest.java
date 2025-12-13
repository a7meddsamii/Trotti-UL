package ca.ulaval.glo4003.trotti.communication.domain.entities;

import ca.ulaval.glo4003.trotti.account.domain.values.Email;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.communication.domain.repositories.ContactRepository;
import ca.ulaval.glo4003.trotti.communication.domain.values.ContactRole;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ContactTest {

    private static final Idul CONTACT_IDUL = Idul.from("jdoe123");
    private static final String CONTACT_NAME = "John Doe";
    private static final Email CONTACT_EMAIL = Email.from("john.doe@ulaval.ca");
    private static final ContactRole CONTACT_ROLE = ContactRole.STUDENT;

    private ContactRepository contactRepository;
    private Contact contact;

    @BeforeEach
    void setup() {
        contactRepository = Mockito.mock(ContactRepository.class);
        Contact.setRepository(contactRepository);

        contact = new Contact(CONTACT_IDUL, CONTACT_NAME, CONTACT_EMAIL, CONTACT_ROLE);
    }

    @Test
    void givenContact_whenSave_thenCallsRepositorySave() {
        contact.save();

        Mockito.verify(contactRepository).save(contact);
    }

    @Test
    void givenIdul_whenFindByIdul_thenCallsRepositoryFindByIdul() {
        Contact expectedContact =
                new Contact(CONTACT_IDUL, CONTACT_NAME, CONTACT_EMAIL, CONTACT_ROLE);
        Mockito.when(contactRepository.findByIdul(CONTACT_IDUL)).thenReturn(expectedContact);

        Contact result = Contact.findByIdul(CONTACT_IDUL);

        Mockito.verify(contactRepository).findByIdul(CONTACT_IDUL);
        Assertions.assertEquals(expectedContact, result);
    }

    @Test
    void givenContactRole_whenFindAllByRole_thenCallsRepositoryFindAllByRole() {
        List<Contact> expectedContacts = List.of(contact);
        Mockito.when(contactRepository.findAllByRole(CONTACT_ROLE)).thenReturn(expectedContacts);

        List<Contact> result = Contact.findAllByRole(CONTACT_ROLE);

        Mockito.verify(contactRepository).findAllByRole(CONTACT_ROLE);
        Assertions.assertEquals(expectedContacts, result);
    }
}
