package ca.ulaval.glo4003.trotti.account.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.account.api.controllers.AccountController;
import ca.ulaval.glo4003.trotti.account.api.controllers.AccountResource;
import ca.ulaval.glo4003.trotti.account.api.controllers.AuthenticationController;
import ca.ulaval.glo4003.trotti.account.api.controllers.AuthenticationResource;
import ca.ulaval.glo4003.trotti.account.api.mappers.AccountApiMapper;
import ca.ulaval.glo4003.trotti.account.application.AccountApplicationService;
import ca.ulaval.glo4003.trotti.config.loaders.Bootstrapper;

public class AccountResourceLoader extends Bootstrapper {
	
	@Override
	public void load() {
		this.loadAccountResource();
		this.loadAuthenticationResource();
	}
	
	private void loadAccountResource() {
		AccountApiMapper accountApiMapper = this.resourceLocator.resolve(AccountApiMapper.class);
		AccountApplicationService accountApplicationService =
				this.resourceLocator.resolve(AccountApplicationService.class);
		
		this.resourceLocator.register(
				AccountResource.class,
				new AccountController(accountApplicationService, accountApiMapper));
	}
	
	private void loadAuthenticationResource() {
		AccountApplicationService accountApplicationService =
				this.resourceLocator.resolve(AccountApplicationService.class);
		AuthenticationResource authenticationController =
				new AuthenticationController(accountApplicationService);
		
		this.resourceLocator.register(AuthenticationResource.class, authenticationController);
	}
}
