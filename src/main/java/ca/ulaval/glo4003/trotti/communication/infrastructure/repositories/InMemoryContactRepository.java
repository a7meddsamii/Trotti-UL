package ca.ulaval.glo4003.trotti.communication.infrastructure.repositories;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.communication.domain.entities.Contact;
import ca.ulaval.glo4003.trotti.communication.domain.repositories.ContactRepository;

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
        return new Contact(foundContact.getIdul(), foundContact.getName(), foundContact.getEmail());
    }
}
