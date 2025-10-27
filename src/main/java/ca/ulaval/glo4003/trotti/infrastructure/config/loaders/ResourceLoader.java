package ca.ulaval.glo4003.trotti.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.api.account.controllers.AccountResource;
import ca.ulaval.glo4003.trotti.api.account.mappers.AccountApiMapper;
import ca.ulaval.glo4003.trotti.api.authentication.controllers.AuthenticationResource;
import ca.ulaval.glo4003.trotti.api.order.controllers.CartResource;
import ca.ulaval.glo4003.trotti.api.order.controllers.OrderResource;
import ca.ulaval.glo4003.trotti.api.order.mappers.OrderApiMapper;
import ca.ulaval.glo4003.trotti.api.order.mappers.PassApiMapper;
import ca.ulaval.glo4003.trotti.api.trip.controllers.TripResource;
import ca.ulaval.glo4003.trotti.api.trip.controllers.UnlockCodeResource;
import ca.ulaval.glo4003.trotti.api.trip.mappers.TripApiMapper;
import ca.ulaval.glo4003.trotti.application.account.AccountApplicationService;
import ca.ulaval.glo4003.trotti.application.order.CartApplicationService;
import ca.ulaval.glo4003.trotti.application.order.OrderApplicationService;
import ca.ulaval.glo4003.trotti.application.trip.TripApplicationService;
import ca.ulaval.glo4003.trotti.application.trip.UnlockCodeApplicationService;
import ca.ulaval.glo4003.trotti.domain.authentication.services.AuthenticationService;

public class ResourceLoader extends Bootstrapper {
    @Override
    public void load() {
        loadAccountResource();
        loadAuthenticationResource();
        loadCartResource();
        loadOrderResource();
        loadUnlockCodeResource();
        loadTripResource();
    }

    private void loadAccountResource() {
        AccountApiMapper accountApiMapper = this.resourceLocator.resolve(AccountApiMapper.class);
        AccountApplicationService accountApplicationService =
                this.resourceLocator.resolve(AccountApplicationService.class);

        this.resourceLocator.register(AccountResource.class,
                new AccountResource(accountApplicationService, accountApiMapper));
    }

    private void loadAuthenticationResource() {
        AccountApplicationService accountApplicationService =
                this.resourceLocator.resolve(AccountApplicationService.class);
        AuthenticationResource authenticationResource =
                new AuthenticationResource(accountApplicationService);

        this.resourceLocator.register(AuthenticationResource.class, authenticationResource);
    }

    private void loadCartResource() {
        PassApiMapper passApiMapper = this.resourceLocator.resolve(PassApiMapper.class);
        AuthenticationService authenticationService =
                this.resourceLocator.resolve(AuthenticationService.class);
        CartApplicationService cartApplicationService =
                this.resourceLocator.resolve(CartApplicationService.class);

        CartResource cartResource =
                new CartResource(cartApplicationService, authenticationService, passApiMapper);
        this.resourceLocator.register(CartResource.class, cartResource);
    }

    private void loadOrderResource() {
        OrderApiMapper orderApiMapper = this.resourceLocator.resolve(OrderApiMapper.class);
        AuthenticationService authenticationService =
                this.resourceLocator.resolve(AuthenticationService.class);
        OrderApplicationService orderApplicationService =
                this.resourceLocator.resolve(OrderApplicationService.class);

        OrderResource orderResource =
                new OrderResource(orderApplicationService, authenticationService, orderApiMapper);
        this.resourceLocator.register(OrderResource.class, orderResource);
    }

    private void loadUnlockCodeResource() {
        AuthenticationService authenticationService =
                this.resourceLocator.resolve(AuthenticationService.class);
        UnlockCodeApplicationService unlockCodeApplicationService =
                this.resourceLocator.resolve(UnlockCodeApplicationService.class);
        UnlockCodeResource unlockCodeResource =
                new UnlockCodeResource(authenticationService, unlockCodeApplicationService);
        this.resourceLocator.register(UnlockCodeResource.class, unlockCodeResource);
    }

    private void loadTripResource() {
        TripApiMapper tripApiMapper = resourceLocator.resolve(TripApiMapper.class);
        TripApplicationService tripApplicationService =
                resourceLocator.resolve(TripApplicationService.class);
        AuthenticationService authenticationService =
                resourceLocator.resolve(AuthenticationService.class);

        TripResource tripResource =
                new TripResource(tripApplicationService, authenticationService, tripApiMapper);
        resourceLocator.register(TripResource.class, tripResource);
    }
}
