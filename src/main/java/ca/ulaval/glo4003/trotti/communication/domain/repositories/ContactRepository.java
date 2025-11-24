package ca.ulaval.glo4003.trotti.communication.domain.repositories;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.communication.domain.entities.Contact;
import ca.ulaval.glo4003.trotti.communication.domain.values.ContactRole;
import java.util.List;

public interface ContactRepository {

    void save(Contact contact);

    Contact findByIdul(Idul idul);

    List<Contact> findAllByRole(ContactRole contactRole);
}
