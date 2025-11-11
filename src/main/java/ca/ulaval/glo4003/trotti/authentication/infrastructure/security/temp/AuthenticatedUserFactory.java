package ca.ulaval.glo4003.trotti.authentication.infrastructure.security.temp;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.authentication.domain.exception.AuthenticationException;
import ca.ulaval.glo4003.trotti.authentication.infrastructure.security.authentication.UserPrincipal;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.SecurityContext;
import org.glassfish.hk2.api.Factory;

public class AuthenticatedUserFactory implements Factory<Idul> {
	
	@Inject
	private SecurityContext securityContext;
	
	@Override
	public Idul provide() {
		if (securityContext == null || securityContext.getUserPrincipal() == null) {
			throw new AuthenticationException("Missing authentication");
		}
		
		UserPrincipal p = (UserPrincipal) securityContext.getUserPrincipal();
		return Idul.from(p.getName());
	}
	
	@Override
	public void dispose(Idul user) {}
}
