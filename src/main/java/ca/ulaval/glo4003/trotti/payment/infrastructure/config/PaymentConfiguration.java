package ca.ulaval.glo4003.trotti.payment.infrastructure.config;

import ca.ulaval.glo4003.trotti.config.Configuration;
import ca.ulaval.glo4003.trotti.config.loaders.DomainServiceLoader;
import ca.ulaval.glo4003.trotti.config.loaders.ForeignServiceLoader;

public class PaymentConfiguration extends Configuration {

    public static Configuration getInstance() {
        if (instance == null) {
            instance = new PaymentConfiguration();
        }

        return instance;
    }

    private PaymentConfiguration() {
        super();
    }

    @Override
	protected void load() {
		new ForeignServiceLoader().load();
		new DomainServiceLoader().load();
	}
}
