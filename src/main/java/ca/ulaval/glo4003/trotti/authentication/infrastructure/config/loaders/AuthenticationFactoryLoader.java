package ca.ulaval.glo4003.trotti.authentication.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.authentication.infrastructure.security.authentication.jwtsecuritycontext.JwtSecurityContextFactoryAdapter;
import ca.ulaval.glo4003.trotti.authentication.infrastructure.security.authentication.SecurityContextFactory;
import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;

public class AuthenticationFactoryLoader extends Bootstrapper {

    @Override
    public void load() {
		this.loadSecurityContextFactory();
    }
	
	private void loadSecurityContextFactory() {
	 this.resourceLocator.register(SecurityContextFactory.class, new JwtSecurityContextFactoryAdapter());
	}
}