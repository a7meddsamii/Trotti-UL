package ca.ulaval.glo4003.trotti.communication.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.communication.domain.EmailMessageFactory;
import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;

public class CommunicationFactoryLoader extends Bootstrapper {
	@Override
	public void load() {
		loadEmailFactory();
	}
	
	private void loadEmailFactory() {
		EmailMessageFactory emailMessageFactory = new EmailMessageFactory();
		this.resourceLocator.register(EmailMessageFactory.class, emailMessageFactory);
	}
}
