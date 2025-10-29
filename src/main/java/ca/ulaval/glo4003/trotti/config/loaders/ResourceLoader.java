package ca.ulaval.glo4003.trotti.config.loaders;

import ca.ulaval.glo4003.trotti.account.api.controllers.AccountController;
import ca.ulaval.glo4003.trotti.account.api.controllers.AccountResource;
import ca.ulaval.glo4003.trotti.account.api.mappers.AccountApiMapper;
import ca.ulaval.glo4003.trotti.account.api.controllers.AuthenticationController;
import ca.ulaval.glo4003.trotti.account.api.controllers.AuthenticationResource;
import ca.ulaval.glo4003.trotti.heartbeat.api.controllers.HeartbeatController;
import ca.ulaval.glo4003.trotti.heartbeat.api.controllers.HeartbeatResource;
import ca.ulaval.glo4003.trotti.order.api.controllers.CartController;
import ca.ulaval.glo4003.trotti.order.api.controllers.CartResource;
import ca.ulaval.glo4003.trotti.order.api.controllers.OrderController;
import ca.ulaval.glo4003.trotti.order.api.controllers.OrderResource;
import ca.ulaval.glo4003.trotti.order.api.mappers.OrderApiMapper;
import ca.ulaval.glo4003.trotti.order.api.mappers.PassApiMapper;
import ca.ulaval.glo4003.trotti.trip.api.controllers.TripController;
import ca.ulaval.glo4003.trotti.trip.api.controllers.TripResource;
import ca.ulaval.glo4003.trotti.trip.api.controllers.UnlockCodeController;
import ca.ulaval.glo4003.trotti.trip.api.controllers.UnlockCodeResource;
import ca.ulaval.glo4003.trotti.trip.api.mappers.TripApiMapper;
import ca.ulaval.glo4003.trotti.account.application.AccountApplicationService;
import ca.ulaval.glo4003.trotti.order.application.CartApplicationService;
import ca.ulaval.glo4003.trotti.order.application.OrderApplicationService;
import ca.ulaval.glo4003.trotti.trip.application.TripApplicationService;
import ca.ulaval.glo4003.trotti.trip.application.UnlockCodeApplicationService;
import ca.ulaval.glo4003.trotti.account.domain.services.AuthenticationService;

public class ResourceLoader extends Bootstrapper {
    @Override
    public void load() {
        loadAccountResource();
        loadAuthenticationResource();
        loadCartResource();
        loadOrderResource();
        loadUnlockCodeResource();
        loadTripResource();
        loadHeartbeatResource();
    }

    private void loadHeartbeatResource() {
        this.resourceLocator.register(HeartbeatResource.class, new HeartbeatController());
    }

    private void loadAccountResource() {
        AccountApiMapper accountApiMapper = this.resourceLocator.resolve(AccountApiMapper.class);
        AccountApplicationService accountApplicationService =
                this.resourceLocator.resolve(AccountApplicationService.class);

        this.resourceLocator.register(AccountResource.class,
                new AccountController(accountApplicationService, accountApiMapper));
    }

    private void loadAuthenticationResource() {
        AccountApplicationService accountApplicationService =
                this.resourceLocator.resolve(AccountApplicationService.class);
        AuthenticationResource authenticationController =
                new AuthenticationController(accountApplicationService);

        this.resourceLocator.register(AuthenticationResource.class, authenticationController);
    }

    private void loadCartResource() {
        PassApiMapper passApiMapper = this.resourceLocator.resolve(PassApiMapper.class);
        AuthenticationService authenticationService =
                this.resourceLocator.resolve(AuthenticationService.class);
        CartApplicationService cartApplicationService =
                this.resourceLocator.resolve(CartApplicationService.class);

        CartResource cartController =
                new CartController(cartApplicationService, authenticationService, passApiMapper);
        this.resourceLocator.register(CartResource.class, cartController);
    }

    private void loadOrderResource() {
        OrderApiMapper orderApiMapper = this.resourceLocator.resolve(OrderApiMapper.class);
        AuthenticationService authenticationService =
                this.resourceLocator.resolve(AuthenticationService.class);
        OrderApplicationService orderApplicationService =
                this.resourceLocator.resolve(OrderApplicationService.class);

        OrderResource orderController =
                new OrderController(orderApplicationService, authenticationService, orderApiMapper);
        this.resourceLocator.register(OrderResource.class, orderController);
    }

    private void loadUnlockCodeResource() {
        AuthenticationService authenticationService =
                this.resourceLocator.resolve(AuthenticationService.class);
        UnlockCodeApplicationService unlockCodeApplicationService =
                this.resourceLocator.resolve(UnlockCodeApplicationService.class);
        UnlockCodeResource unlockCodeController =
                new UnlockCodeController(authenticationService, unlockCodeApplicationService);
        this.resourceLocator.register(UnlockCodeResource.class, unlockCodeController);
    }

    private void loadTripResource() {
        TripApiMapper tripApiMapper = resourceLocator.resolve(TripApiMapper.class);
        TripApplicationService tripApplicationService =
                resourceLocator.resolve(TripApplicationService.class);
        AuthenticationService authenticationService =
                resourceLocator.resolve(AuthenticationService.class);

        TripResource tripController =
                new TripController(tripApplicationService, authenticationService, tripApiMapper);
        resourceLocator.register(TripResource.class, tripController);
    }
}
