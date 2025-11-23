package ca.ulaval.glo4003.trotti.communication.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.communication.domain.entities.Contact;
import ca.ulaval.glo4003.trotti.communication.domain.repositories.ContactRepository;
import ca.ulaval.glo4003.trotti.communication.infrastructure.repositories.InMemoryContactRepository;
import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;

public class RepositoryLoader extends Bootstrapper {

    @Override
    public void load() {
        ContactRepository contactRepository = new InMemoryContactRepository();
        Contact.setRepository(contactRepository);
    }
}
