package ca.ulaval.glo4003.trotti.authentication.infrastructure.security.authentication.jwtsecuritycontext;

import ca.ulaval.glo4003.trotti.authentication.domain.values.AuthenticatedIdentity;
import ca.ulaval.glo4003.trotti.authentication.infrastructure.security.authentication.SecurityContextFactory;
import jakarta.ws.rs.core.SecurityContext;

public class SecurityContextFactoryAdapter implements SecurityContextFactory {

    @Override
    public SecurityContext create(AuthenticatedIdentity identity, boolean secure) {
		JwtPrincipal principal = new JwtPrincipal(identity.idul(), identity.role(), identity.permissions());
        return new JwtSecurityContext(principal, secure);
    }
}
