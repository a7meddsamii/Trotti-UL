package ca.ulaval.glo4003.trotti.account.infrastructure.security.authentication;

import ca.ulaval.glo4003.trotti.account.domain.exceptions.AuthenticationException;
import ca.ulaval.glo4003.trotti.account.domain.services.SessionTokenProvider;
import ca.ulaval.glo4003.trotti.account.domain.values.AuthenticatedIdentity;
import ca.ulaval.glo4003.trotti.account.domain.values.SessionToken;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.ext.Provider;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    @Inject
    private SessionTokenProvider sessionTokenProvider;
    @Inject
    private SecurityContextFactory securityContextFactory;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) {
        String token = containerRequestContext.getHeaderString("Authorization");
		
        if (token == null || token.isBlank()) {
            return;
        }

        try {
            AuthenticatedIdentity authenticatedIdentity =
                    this.sessionTokenProvider.deserialize(SessionToken.from(token));
            boolean secure = containerRequestContext.getSecurityContext().isSecure();
            SecurityContext securityContext =
                    securityContextFactory.create(authenticatedIdentity, secure);
            containerRequestContext.setSecurityContext(securityContext);
        } catch (AuthenticationException exception) {
            containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                    .entity(exception.getMessage()).build());
        }
    }
}
