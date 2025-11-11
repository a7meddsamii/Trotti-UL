package ca.ulaval.glo4003.trotti.authentication.infrastructure.security.authentication;

import ca.ulaval.glo4003.trotti.authentication.domain.exception.AuthenticationException;
import ca.ulaval.glo4003.trotti.authentication.domain.services.SessionTokenGenerator;
import ca.ulaval.glo4003.trotti.authentication.domain.values.AuthenticatedIdentity;
import ca.ulaval.glo4003.trotti.authentication.domain.values.SessionToken;
import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.ext.Provider;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {
    private final SessionTokenGenerator sessionTokenGenerator;
	private final SecurityContextFactory securityContextFactory;

    public AuthenticationFilter(SessionTokenGenerator sessionTokenGenerator, SecurityContextFactory securityContextFactory) {
        this.sessionTokenGenerator = sessionTokenGenerator;
		this.securityContextFactory = securityContextFactory;
	}

    @Override
    public void filter(ContainerRequestContext containerRequestContext) {
        String token = containerRequestContext.getHeaderString("Authorization"); // TODO recheck this Bearer or Authorization

        try {
            AuthenticatedIdentity authenticatedIdentity = this.sessionTokenGenerator.deserialize(SessionToken.from(token));
            boolean secure = containerRequestContext.getSecurityContext().isSecure();
			SecurityContext securityContext = securityContextFactory.create(authenticatedIdentity, secure);
            containerRequestContext.setSecurityContext(securityContext);
        }catch ( AuthenticationException exception) {
            containerRequestContext.abortWith(
                Response.status(Response.Status.UNAUTHORIZED)
                    .entity(exception.getMessage())
                    .build()
            );
        }
    }
}
