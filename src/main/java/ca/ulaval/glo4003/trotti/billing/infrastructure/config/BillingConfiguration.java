package ca.ulaval.glo4003.trotti.billing.infrastructure.config;

import ca.ulaval.glo4003.trotti.billing.infrastructure.config.loaders.BillingForeignServiceLoader;
import ca.ulaval.glo4003.trotti.billing.infrastructure.config.loaders.order.*;
import ca.ulaval.glo4003.trotti.billing.infrastructure.config.loaders.payment.PaymentFactoryLoader;
import ca.ulaval.glo4003.trotti.billing.infrastructure.config.loaders.payment.PaymentGatewayLoader;
import ca.ulaval.glo4003.trotti.billing.infrastructure.config.loaders.ridepermit.*;
import ca.ulaval.glo4003.trotti.config.Configuration;

public class BillingConfiguration extends Configuration {

    protected static Configuration instance;

    public static Configuration getInstance() {
        if (instance == null) {
            instance = new BillingConfiguration();
        }

        return instance;
    }

    private BillingConfiguration() {
        super();
    }

    @Override
    protected void load() {
        new BillingForeignServiceLoader().load();

        new PaymentFactoryLoader().load();
        new PaymentGatewayLoader().load();

        new OrderFactoryLoader().load();
        new OrderMapperLoader().load();
        new OrderProviderLoader().load();
        new OrderRepositoryLoader().load();
        new OrderAssemblerLoader().load();
        new OrderApplicationServiceLoader().load();
        new OrderResourceLoader().load();

        new RidePermitAssemblerLoader().load();
        new RidePermitFactoryLoader().load();
        new RidePermitServiceLoader().load();
        new RidePermitRepositoryLoader().load();
        new RidePermitApplicationServiceLoader().load();
        new RidePermitGatewayEntryLoader().load();
        new RidePermitResourceLoader().load();
    }
}
