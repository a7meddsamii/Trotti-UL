package ca.ulaval.glo4003.trotti.account.infrastructure.security.identity;

import ca.ulaval.glo4003.trotti.account.domain.exceptions.AuthenticationException;
import ca.ulaval.glo4003.trotti.account.infrastructure.security.authentication.UserPrincipal;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.SecurityContext;
import org.glassfish.hk2.api.Factory;

public class AuthenticatedUserFactory implements Factory<Idul> {
    @Context
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
