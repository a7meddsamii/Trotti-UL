package ca.ulaval.glo4003.trotti.authentication.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.authentication.application.AuthenticationApplicationService;
import ca.ulaval.glo4003.trotti.authentication.application.AuthenticationService;
import ca.ulaval.glo4003.trotti.authentication.domain.gateway.IdentityGateway;
import ca.ulaval.glo4003.trotti.authentication.domain.services.SessionTokenGenerator;
import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;

public class AuthenticationApplicationServiceLoader extends Bootstrapper {

    @Override
    public void load() {
        loadAuthenticationApplicationService();
    }
	
	private void loadAuthenticationApplicationService(){
		SessionTokenGenerator sessionTokenGenerator = this.resourceLocator.resolve(SessionTokenGenerator.class);
		IdentityGateway identityGateway = this.resourceLocator.resolve(IdentityGateway.class);
		AuthenticationService authenticationApplicationService = new AuthenticationApplicationService(sessionTokenGenerator, identityGateway);
		this.resourceLocator.register(AuthenticationService.class, authenticationApplicationService);
	}
}
