package ca.ulaval.glo4003.trotti.communication.domain.entities;

import ca.ulaval.glo4003.trotti.account.domain.values.Email;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.communication.domain.repositories.ContactRepository;

public class Contact {

    private static ContactRepository repository;

    private final Idul idul;
    private final String name;
    private final Email email;

    public Contact(Idul idul, String name, Email email) {
        this.idul = idul;
        this.email = email;
        this.name = name;
    }

    public static void setRepository(ContactRepository repository) {
        Contact.repository = repository;
    }

    public void save() {
        repository.save(this);
    }

    public Idul getIdul() {
        return idul;
    }

    public String getName() {
        return name;
    }

    public Email getEmail() {
        return email;
    }

    public static Contact findByIdul(Idul idul) {
        return repository.findByIdul(idul);
    }
}
