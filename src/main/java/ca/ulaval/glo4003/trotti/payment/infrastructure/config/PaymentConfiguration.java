package ca.ulaval.glo4003.trotti.payment.infrastructure.config;

import ca.ulaval.glo4003.trotti.config.Configuration;
import ca.ulaval.glo4003.trotti.payment.infrastructure.config.loaders.PaymentDomainServiceLoader;
import ca.ulaval.glo4003.trotti.payment.infrastructure.config.loaders.PaymentForeignServiceLoader;

public class PaymentConfiguration extends Configuration {

    protected static Configuration instance;

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
        new PaymentForeignServiceLoader().load();
        new PaymentDomainServiceLoader().load();
    }
}
