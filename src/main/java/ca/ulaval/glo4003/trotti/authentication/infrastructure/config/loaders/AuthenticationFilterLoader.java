package ca.ulaval.glo4003.trotti.authentication.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.authentication.api_temp.TempController;
import ca.ulaval.glo4003.trotti.authentication.application.AuthenticationService;
import ca.ulaval.glo4003.trotti.authentication.domain.services.SessionTokenGenerator;
import ca.ulaval.glo4003.trotti.authentication.infrastructure.security.authentication.AuthenticationFilter;
import ca.ulaval.glo4003.trotti.authentication.infrastructure.security.authentication.SecurityContextFactory;
import ca.ulaval.glo4003.trotti.authentication.infrastructure.security.authorization.AuthorizationFilter;
import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;

public class AuthenticationFilterLoader extends Bootstrapper {
	
	@Override
	public void load() {
//		this.loadAuthenticationFilter();
//		this.loadAuthorizationFilter();
		this.loadTempController();
	}
	
	private void loadAuthenticationFilter() {
		SecurityContextFactory securityContextFactory = this.resourceLocator.resolve(SecurityContextFactory.class);
		SessionTokenGenerator sessionTokenGenerator = this.resourceLocator.resolve(SessionTokenGenerator.class);
//		this.resourceLocator.register(AuthenticationFilter.class, new AuthenticationFilter(sessionTokenGenerator, securityContextFactory));

	}
	
	private void loadAuthorizationFilter(){
		this.resourceLocator.register(AuthorizationFilter.class, new AuthorizationFilter());
	}
	
	private void loadTempController(){
		// TODO temporary
		AuthenticationService authenticationService = this.resourceLocator.resolve(AuthenticationService.class);
		this.resourceLocator.register(TempController.class, new TempController(authenticationService));
	}
}
