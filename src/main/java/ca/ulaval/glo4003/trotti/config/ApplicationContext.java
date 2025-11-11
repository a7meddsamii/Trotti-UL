package ca.ulaval.glo4003.trotti.config;

import ca.ulaval.glo4003.trotti.account.infrastructure.config.AccountConfiguration;
import ca.ulaval.glo4003.trotti.authentication.infrastructure.config.AuthenticationConfiguration;
import ca.ulaval.glo4003.trotti.commons.infrastructure.config.CommonsConfiguration;
import ca.ulaval.glo4003.trotti.communication.infrastructure.config.CommunicationConfiguration;
import ca.ulaval.glo4003.trotti.config.locator.ComponentLocator;
import ca.ulaval.glo4003.trotti.order.infrastructure.config.OrderConfiguration;
import ca.ulaval.glo4003.trotti.payment.infrastructure.config.PaymentConfiguration;
import ca.ulaval.glo4003.trotti.trip.infrastructure.config.TripConfiguration;
import java.time.Clock;

public class ApplicationContext extends Configuration {
    protected static Configuration instance;

    public static Configuration getInstance() {
        if (instance == null) {
            instance = new ApplicationContext();
        }

        return instance;
    }

    @Override
    protected void load() {
        ComponentLocator locator = ComponentLocator.getInstance();
        locator.register(Clock.class, Clock.systemDefaultZone());

        CommonsConfiguration.getInstance().load();
        AccountConfiguration.getInstance().load();
        CommunicationConfiguration.getInstance().load();
        PaymentConfiguration.getInstance().load();
        OrderConfiguration.getInstance().load();
        TripConfiguration.getInstance().load();
		AuthenticationConfiguration.getInstance().load();
    }
}
