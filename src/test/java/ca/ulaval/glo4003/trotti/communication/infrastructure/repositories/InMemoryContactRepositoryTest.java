package ca.ulaval.glo4003.trotti.communication.infrastructure.repositories;

import ca.ulaval.glo4003.trotti.account.domain.values.Email;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.communication.domain.entities.Contact;
import ca.ulaval.glo4003.trotti.communication.domain.values.ContactRole;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InMemoryContactRepositoryTest {

    private static final Idul STUDENT_IDUL = Idul.from("stud123");
    private static final String STUDENT_NAME = "John Student";
    private static final Email STUDENT_EMAIL = Email.from("student@ulaval.ca");
    private static final ContactRole STUDENT_ROLE = ContactRole.STUDENT;
    private static final Idul TECHNICIAN_IDUL = Idul.from("tech456");
    private static final String TECHNICIAN_NAME = "Jane Tech";
    private static final Email TECHNICIAN_EMAIL = Email.from("tech@ulaval.ca");
    private static final ContactRole TECHNICIAN_ROLE = ContactRole.TECHNICIAN;

    private Contact studentContact;
    private Contact technicianContact;
    private InMemoryContactRepository repository;

    @BeforeEach
    void setup() {
        repository = new InMemoryContactRepository();
        studentContact = new Contact(STUDENT_IDUL, STUDENT_NAME, STUDENT_EMAIL, STUDENT_ROLE);
        technicianContact =
                new Contact(TECHNICIAN_IDUL, TECHNICIAN_NAME, TECHNICIAN_EMAIL, TECHNICIAN_ROLE);
    }

    @Test
    void givenContact_whenSave_thenContactIsStored() {
        repository.save(studentContact);

        Contact result = repository.findByIdul(STUDENT_IDUL);

        Assertions.assertNotNull(result);
    }

    @Test
    void givenExistingIdul_whenFindByIdul_thenReturnsContact() {
        repository.save(studentContact);

        Contact result = repository.findByIdul(STUDENT_IDUL);

        Assertions.assertEquals(STUDENT_IDUL, result.getIdul());
    }

    @Test
    void givenContactsWithSameRole_whenFindAllByRole_thenReturnsAllMatchingContacts() {
        Contact anotherStudent = new Contact(Idul.from("student789"), "Bob Student",
                Email.from("bob@ulaval.ca"), STUDENT_ROLE);
        repository.save(studentContact);
        repository.save(anotherStudent);
        repository.save(technicianContact);

        List<Contact> result = repository.findAllByRole(STUDENT_ROLE);

        Assertions.assertEquals(2, result.size());
        Assertions.assertTrue(result.stream().allMatch(c -> c.getRole().equals(STUDENT_ROLE)));
    }

    @Test
    void givenNoContactsWithRole_whenFindAllByRole_thenReturnsEmptyList() {
        repository.save(studentContact);

        List<Contact> result = repository.findAllByRole(TECHNICIAN_ROLE);

        Assertions.assertTrue(result.isEmpty());
    }
}
