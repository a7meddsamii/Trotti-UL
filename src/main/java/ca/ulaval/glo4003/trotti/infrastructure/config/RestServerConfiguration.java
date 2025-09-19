package ca.ulaval.glo4003.trotti.infrastructure.config;

import ca.ulaval.glo4003.trotti.infrastructure.config.binders.ApplicationServiceBinder;
import ca.ulaval.glo4003.trotti.infrastructure.config.binders.ServerResourceInstantiator;
import io.jsonwebtoken.Jwts;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import javax.crypto.SecretKey;

public class RestServerConfiguration extends AbstractBinder {
	private static final SecretKey secretKey = Jwts.SIG.HS256.key().build();
	
	@Override
	protected void configure() {
		ServerResourceInstantiator.getInstance().initiate();
		install(new ApplicationServiceBinder());
		// Add more binders as needed here
	}
}