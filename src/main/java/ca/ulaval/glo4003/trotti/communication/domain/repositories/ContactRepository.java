package ca.ulaval.glo4003.trotti.communication.domain.repositories;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.communication.domain.entities.Contact;

public interface ContactRepository {

    void save(Contact contact);

    Contact findByIdul(Idul idul);
}
