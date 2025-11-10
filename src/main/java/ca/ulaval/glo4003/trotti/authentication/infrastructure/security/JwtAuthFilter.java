package ca.ulaval.glo4003.trotti.authentication.infrastructure.security;

import ca.ulaval.glo4003.trotti.authentication.domain.values.AuthenticatedIdentity;
import ca.ulaval.glo4003.trotti.authentication.domain.values.SessionToken;
import ca.ulaval.glo4003.trotti.authentication.infrastructure.services.SessionTokenServiceAdapter;
import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import java.io.IOException;

@Priority(Priorities.AUTHENTICATION)
public class JwtAuthFilter implements ContainerRequestFilter {
    private final SessionTokenServiceAdapter sessionTokenService;

    public JwtAuthFilter(SessionTokenServiceAdapter sessionTokenService) {
        this.sessionTokenService = sessionTokenService;
    }

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {

        String token = containerRequestContext.getHeaderString("Authorization");

        try {
            AuthenticatedIdentity authenticatedIdentity = sessionTokenService.deserialize(SessionToken.from(token));
            JwtPrincipal principal = toPrincipal(authenticatedIdentity);

            boolean secure = containerRequestContext.getSecurityContext().isSecure();
            containerRequestContext.setSecurityContext(new JwtSecurityContext(principal, secure));
        }catch ( Exception e) {
            containerRequestContext.abortWith(
                Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Invalid token")
                    .build()
            );
        }
    }

    private JwtPrincipal toPrincipal(AuthenticatedIdentity identity) {
        return new JwtPrincipal(identity.idul(), identity.role(), identity.permissions());
    }
}
