package ca.ulaval.glo4003.trotti.config.jersey;

import ca.ulaval.glo4003.trotti.account.api.controllers.AccountResource;
import ca.ulaval.glo4003.trotti.account.api.controllers.AuthenticationResource;
import ca.ulaval.glo4003.trotti.account.domain.services.SessionTokenProvider;
import ca.ulaval.glo4003.trotti.account.infrastructure.security.authentication.SecurityContextFactory;
import ca.ulaval.glo4003.trotti.account.infrastructure.security.identity.AuthenticatedUserFactory;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.config.locator.ComponentLocator;
import ca.ulaval.glo4003.trotti.heartbeat.api.controllers.HeartbeatResource;
import ca.ulaval.glo4003.trotti.order.api.controllers.CartResource;
import ca.ulaval.glo4003.trotti.order.api.controllers.OrderResource;
import ca.ulaval.glo4003.trotti.trip.api.controllers.TripResource;
import ca.ulaval.glo4003.trotti.trip.api.controllers.UnlockCodeResource;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class JerseyBinder extends AbstractBinder {

    @Override
    protected void configure() {
        ComponentLocator locator = ComponentLocator.getInstance();

        bind(locator.resolve(SecurityContextFactory.class)).to(SecurityContextFactory.class);
        bind(locator.resolve(SessionTokenProvider.class)).to(SessionTokenProvider.class);
		bindFactory(AuthenticatedUserFactory.class)
				.to(Idul.class)
				.proxy(true)
				.proxyForSameScope(false);

        bind(locator.resolve(AccountResource.class)).to(AccountResource.class);
        bind(locator.resolve(AuthenticationResource.class)).to(AuthenticationResource.class);
        bind(locator.resolve(CartResource.class)).to(CartResource.class);
        bind(locator.resolve(HeartbeatResource.class)).to(HeartbeatResource.class);
        bind(locator.resolve(OrderResource.class)).to(OrderResource.class);
        bind(locator.resolve(UnlockCodeResource.class)).to(UnlockCodeResource.class);
        bind(locator.resolve(TripResource.class)).to(TripResource.class);
    }
}
