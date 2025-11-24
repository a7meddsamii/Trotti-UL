package ca.ulaval.glo4003.trotti.communication.domain.entities;

import ca.ulaval.glo4003.trotti.account.domain.values.Email;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.communication.domain.repositories.ContactRepository;
import ca.ulaval.glo4003.trotti.communication.domain.values.ContactRole;
import java.util.List;

public class Contact {

    private static ContactRepository repository;

    private final Idul idul;
    private final String name;
    private final Email email;
    private final ContactRole role;

    public Contact(Idul idul, String name, Email email, ContactRole role) {
        this.idul = idul;
        this.email = email;
        this.name = name;
        this.role = role;
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

    public ContactRole getRole() {
        return role;
    }

    public static Contact findByIdul(Idul idul) {
        return repository.findByIdul(idul);
    }

    public static List<Contact> findAllByRole(ContactRole contactRole) {
        return repository.findAllByRole(contactRole);
    }
}
