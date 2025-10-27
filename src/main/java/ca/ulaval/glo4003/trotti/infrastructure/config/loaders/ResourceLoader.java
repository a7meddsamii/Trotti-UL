package ca.ulaval.glo4003.trotti.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.api.account.controllers.AccountController;
import ca.ulaval.glo4003.trotti.api.account.controllers.AccountResource;
import ca.ulaval.glo4003.trotti.api.account.mappers.AccountApiMapper;
import ca.ulaval.glo4003.trotti.api.authentication.controllers.AuthenticationController;
import ca.ulaval.glo4003.trotti.api.authentication.controllers.AuthenticationResource;
import ca.ulaval.glo4003.trotti.api.heartbeat.controllers.HeartbeatController;
import ca.ulaval.glo4003.trotti.api.heartbeat.controllers.HeartbeatResource;
import ca.ulaval.glo4003.trotti.api.order.controllers.CartController;
import ca.ulaval.glo4003.trotti.api.order.controllers.CartResource;
import ca.ulaval.glo4003.trotti.api.order.controllers.OrderController;
import ca.ulaval.glo4003.trotti.api.order.controllers.OrderResource;
import ca.ulaval.glo4003.trotti.api.order.mappers.OrderApiMapper;
import ca.ulaval.glo4003.trotti.api.order.mappers.PassApiMapper;
import ca.ulaval.glo4003.trotti.api.trip.controllers.TravelerController;
import ca.ulaval.glo4003.trotti.api.trip.controllers.TravelerResource;
import ca.ulaval.glo4003.trotti.api.trip.controllers.UnlockCodeController;
import ca.ulaval.glo4003.trotti.api.trip.controllers.UnlockCodeResource;
import ca.ulaval.glo4003.trotti.application.account.AccountApplicationService;
import ca.ulaval.glo4003.trotti.application.order.CartApplicationService;
import ca.ulaval.glo4003.trotti.application.order.OrderApplicationService;
import ca.ulaval.glo4003.trotti.application.trip.RidePermitActivationApplicationService;
import ca.ulaval.glo4003.trotti.application.trip.UnlockCodeApplicationService;
import ca.ulaval.glo4003.trotti.domain.authentication.services.AuthenticationService;

public class ResourceLoader extends Bootstrapper {
    @Override
    public void load() {
        loadAccountResource();
        loadAuthenticationResource();
        loadTravelerResource();
        loadCartResource();
        loadOrderResource();
        loadUnlockCodeResource();
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

    private void loadTravelerResource() {
        RidePermitActivationApplicationService ridePermitActivationApplicationService =
                this.resourceLocator.resolve(RidePermitActivationApplicationService.class);
        AuthenticationService authenticationService =
                this.resourceLocator.resolve(AuthenticationService.class);

        this.resourceLocator.register(TravelerResource.class, new TravelerController(
                ridePermitActivationApplicationService, authenticationService));
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
}
