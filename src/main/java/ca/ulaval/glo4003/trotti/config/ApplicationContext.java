package ca.ulaval.glo4003.trotti.config;

import ca.ulaval.glo4003.trotti.account.infrastructure.config.AccountConfiguration;
import ca.ulaval.glo4003.trotti.billing.infrastructure.config.BillingConfiguration;
import ca.ulaval.glo4003.trotti.commons.infrastructure.config.CommonsConfiguration;
import ca.ulaval.glo4003.trotti.commons.infrastructure.config.EventSubscriptionConfiguration;
import ca.ulaval.glo4003.trotti.communication.infrastructure.config.CommunicationConfiguration;
import ca.ulaval.glo4003.trotti.config.locator.ComponentLocator;
import ca.ulaval.glo4003.trotti.heartbeat.infrastructure.config.HeartbeatConfiguration;
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
        HeartbeatConfiguration.getInstance().load();
        BillingConfiguration.getInstance().load();
        CommunicationConfiguration.getInstance().load();
        TripConfiguration.getInstance().load();

        EventSubscriptionConfiguration.getInstance().load();

    }
}
