package ca.ulaval.glo4003.trotti.communication.infrastructure.repositories;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.communication.domain.entities.Contact;
import ca.ulaval.glo4003.trotti.communication.domain.repositories.ContactRepository;
import ca.ulaval.glo4003.trotti.communication.domain.values.ContactRole;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryContactRepository implements ContactRepository {

    private final Map<Idul, Contact> contacts = new ConcurrentHashMap<>();

    @Override
    public void save(Contact contact) {
        contacts.put(contact.getIdul(), contact);
    }

    @Override
    public Contact findByIdul(Idul idul) {
        Contact foundContact = contacts.get(idul);
        return new Contact(foundContact.getIdul(), foundContact.getName(), foundContact.getEmail(),
                foundContact.getRole());
    }

    @Override
    public List<Contact> findAllByRole(ContactRole contactRole) {
        return contacts.values().stream().filter(c -> c.getRole().equals(contactRole))
                .map(c -> new Contact(c.getIdul(), c.getName(), c.getEmail(), c.getRole()))
                .toList();
    }
}
