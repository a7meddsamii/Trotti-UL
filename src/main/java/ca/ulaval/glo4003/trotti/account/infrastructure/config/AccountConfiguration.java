package ca.ulaval.glo4003.trotti.account.infrastructure.config;
import ca.ulaval.glo4003.trotti.account.infrastructure.config.loaders.*;
import ca.ulaval.glo4003.trotti.config.Configuration;

public class AccountConfiguration extends Configuration {
	
	public static Configuration getInstance() {
		if (instance == null) {
			instance = new AccountConfiguration();
		}
		return instance;
	}

	@Override
	protected void load() {
		new AccountMapperLoader().load();
		new AccountRepositoryLoader().load();
		new AccountFactoryLoader().load();
		new AccountApplicationLoader().load();
		new AccountResourceLoader().load();
	}
}
