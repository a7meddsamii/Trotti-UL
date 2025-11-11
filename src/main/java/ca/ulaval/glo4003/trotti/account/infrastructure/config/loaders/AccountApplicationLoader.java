package ca.ulaval.glo4003.trotti.account.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.account.application.AccountApplicationService;
import ca.ulaval.glo4003.trotti.account.application.port.AccountService;
import ca.ulaval.glo4003.trotti.account.domain.factories.AccountFactory;
import ca.ulaval.glo4003.trotti.account.domain.repositories.AccountRepository;
import ca.ulaval.glo4003.trotti.account.domain.services.AuthenticationService;
import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;

public class AccountApplicationLoader extends Bootstrapper {
    @Override
    public void load() {
        loadAccountApplicationService();
    }
	
	/**
	 * @deprecated this line will be removed in the future 
	 * <pre><code>this.resourceLocator.register(AccountApplicationService.class, accountApplicationService);</code></pre>
	 * The {@link AccountResourceLoader} needs to be adjusted accordingly to use the AccountService interface instead
	 */
    private void loadAccountApplicationService() {
        AccountRepository accountRepository = this.resourceLocator.resolve(AccountRepository.class);
        AuthenticationService authenticationService =
                this.resourceLocator.resolve(AuthenticationService.class);
        AccountFactory accountFactory = this.resourceLocator.resolve(AccountFactory.class);
        AccountApplicationService accountApplicationService = new AccountApplicationService(
                accountRepository, authenticationService, accountFactory);

        this.resourceLocator.register(AccountApplicationService.class, accountApplicationService);
		this.resourceLocator.register(AccountService.class, accountApplicationService);
    }
}
