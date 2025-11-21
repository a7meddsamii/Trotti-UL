package ca.ulaval.glo4003.trotti.account.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.account.application.AccountApplicationService;
import ca.ulaval.glo4003.trotti.account.domain.factories.AccountFactory;
import ca.ulaval.glo4003.trotti.account.domain.repositories.AccountRepository;
import ca.ulaval.glo4003.trotti.account.domain.services.SessionTokenProvider;
import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;

public class AccountApplicationLoader extends Bootstrapper {
    @Override
    public void load() {
        loadAccountApplicationService();
    }

    private void loadAccountApplicationService() {
        AccountRepository accountRepository = this.resourceLocator.resolve(AccountRepository.class);
        SessionTokenProvider sessionTokenProvider =
                this.resourceLocator.resolve(SessionTokenProvider.class);
        AccountFactory accountFactory = this.resourceLocator.resolve(AccountFactory.class);
		AccountApplicationService accountApplicationService = new AccountApplicationService(accountRepository,
                accountFactory, sessionTokenProvider);

        this.resourceLocator.register(AccountApplicationService.class, accountApplicationService);
    }
}
