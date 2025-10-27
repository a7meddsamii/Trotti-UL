package ca.ulaval.glo4003.trotti.infrastructure.config;

import ca.ulaval.glo4003.trotti.api.account.controllers.AccountResource;
import ca.ulaval.glo4003.trotti.api.authentication.controllers.AuthenticationResource;
import ca.ulaval.glo4003.trotti.api.heartbeat.controllers.HeartbeatResource;
import ca.ulaval.glo4003.trotti.api.order.controllers.CartResource;
import ca.ulaval.glo4003.trotti.api.order.controllers.OrderResource;
import ca.ulaval.glo4003.trotti.api.trip.controllers.TravelerResource;
import ca.ulaval.glo4003.trotti.api.trip.controllers.UnlockCodeResource;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class RestServerConfiguration extends AbstractBinder {

    @Override
    protected void configure() {
        ServerComponentLocator locator = ServerComponentLocator.getInstance();

        bind(locator.resolve(AccountResource.class)).to(AccountResource.class);
        bind(locator.resolve(AuthenticationResource.class)).to(AuthenticationResource.class);
        bind(locator.resolve(CartResource.class)).to(CartResource.class);
        bind(locator.resolve(HeartbeatResource.class)).to(HeartbeatResource.class);
        bind(locator.resolve(OrderResource.class)).to(OrderResource.class);
        bind(locator.resolve(TravelerResource.class)).to(TravelerResource.class);
        bind(locator.resolve(UnlockCodeResource.class)).to(UnlockCodeResource.class);
    }
}
