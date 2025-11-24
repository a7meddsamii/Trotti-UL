package ca.ulaval.glo4003.trotti.config.jersey;

import ca.ulaval.glo4003.trotti.account.api.controllers.AccountResource;
import ca.ulaval.glo4003.trotti.account.api.controllers.AuthenticationResource;
import ca.ulaval.glo4003.trotti.account.api.security.authentication.SecurityContextFactory;
import ca.ulaval.glo4003.trotti.account.domain.services.SessionTokenProvider;
import ca.ulaval.glo4003.trotti.billing.api.order.controller.OrderResource;
import ca.ulaval.glo4003.trotti.billing.api.ridepermit.controller.RidePermitResource;
import ca.ulaval.glo4003.trotti.config.locator.ComponentLocator;
import ca.ulaval.glo4003.trotti.heartbeat.api.controllers.HeartbeatResource;
import ca.ulaval.glo4003.trotti.trip.api.controllers.StationResource;
import ca.ulaval.glo4003.trotti.trip.api.controllers.TripResource;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class JerseyBinder extends AbstractBinder {

    @Override
    protected void configure() {
        ComponentLocator locator = ComponentLocator.getInstance();

        bind(locator.resolve(SecurityContextFactory.class)).to(SecurityContextFactory.class);
        bind(locator.resolve(SessionTokenProvider.class)).to(SessionTokenProvider.class);

        bind(locator.resolve(AccountResource.class)).to(AccountResource.class);
        bind(locator.resolve(AuthenticationResource.class)).to(AuthenticationResource.class);
        bind(locator.resolve(HeartbeatResource.class)).to(HeartbeatResource.class);
        bind(locator.resolve(OrderResource.class)).to(OrderResource.class);
        bind(locator.resolve(RidePermitResource.class)).to(RidePermitResource.class);
        bind(locator.resolve(TripResource.class)).to(TripResource.class);
        bind(locator.resolve(StationResource.class)).to(StationResource.class);
    }
}
