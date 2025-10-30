package ca.ulaval.glo4003.trotti.account.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.account.application.AccountApplicationService;
import ca.ulaval.glo4003.trotti.account.domain.factories.AccountFactory;
import ca.ulaval.glo4003.trotti.account.domain.repositories.AccountRepository;
import ca.ulaval.glo4003.trotti.account.domain.services.AuthenticationService;
import ca.ulaval.glo4003.trotti.config.loaders.Bootstrapper;

public class AccountApplicationLoader extends Bootstrapper {
    @Override
    public void load() {
        loadAccountApplicationService();
    }

    private void loadAccountApplicationService() {
        AccountRepository accountRepository = this.resourceLocator.resolve(AccountRepository.class);
        AuthenticationService authenticationService =
                this.resourceLocator.resolve(AuthenticationService.class);
        AccountFactory accountFactory = this.resourceLocator.resolve(AccountFactory.class);
        AccountApplicationService accountApplicationService = new AccountApplicationService(
                accountRepository, authenticationService, accountFactory);

        this.resourceLocator.register(AccountApplicationService.class, accountApplicationService);
    }
}
