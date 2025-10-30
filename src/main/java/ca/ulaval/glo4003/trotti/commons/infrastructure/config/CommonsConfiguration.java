package ca.ulaval.glo4003.trotti.commons.infrastructure.config;

import ca.ulaval.glo4003.trotti.commons.infrastructure.config.loaders.RegistryLoader;
import ca.ulaval.glo4003.trotti.config.Configuration;
import ca.ulaval.glo4003.trotti.order.infrastructure.config.loaders.*;

public class CommonsConfiguration extends Configuration {
    private CommonsConfiguration() {
        super();
    }
	
	public static Configuration getInstance() {
		if (instance == null) {
			instance = new CommonsConfiguration();
		}
		
		return instance;
	}
	
	@Override
	protected void load() {
		new RegistryLoader().load();
	}
}
