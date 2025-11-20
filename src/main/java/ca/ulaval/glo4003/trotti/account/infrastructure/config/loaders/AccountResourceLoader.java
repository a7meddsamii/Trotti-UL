package ca.ulaval.glo4003.trotti.account.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.account.api.controllers.AccountController;
import ca.ulaval.glo4003.trotti.account.api.controllers.AccountResource;
import ca.ulaval.glo4003.trotti.account.api.controllers.AuthenticationController;
import ca.ulaval.glo4003.trotti.account.api.controllers.AuthenticationResource;
import ca.ulaval.glo4003.trotti.account.api.mappers.AccountApiMapper;
import ca.ulaval.glo4003.trotti.account.application.AccountService;
import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;

public class AccountResourceLoader extends Bootstrapper {

    @Override
    public void load() {
        this.loadAccountResource();
        this.loadAuthenticationResource();
    }

    private void loadAccountResource() {
        AccountApiMapper accountApiMapper = this.resourceLocator.resolve(AccountApiMapper.class);
        AccountService accountApplicationService =
                this.resourceLocator.resolve(AccountService.class);

        this.resourceLocator.register(AccountResource.class,
                new AccountController(accountApplicationService, accountApiMapper));
    }

    private void loadAuthenticationResource() {
        AccountService accountApplicationService =
                this.resourceLocator.resolve(AccountService.class);
        AuthenticationResource authenticationController =
                new AuthenticationController(accountApplicationService);

        this.resourceLocator.register(AuthenticationResource.class, authenticationController);
    }
}
