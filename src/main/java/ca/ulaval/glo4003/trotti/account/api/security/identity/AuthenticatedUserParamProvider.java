package ca.ulaval.glo4003.trotti.account.api.security.identity;

import ca.ulaval.glo4003.trotti.account.domain.exceptions.AuthenticationException;
import ca.ulaval.glo4003.trotti.account.api.security.authentication.UserPrincipal;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import jakarta.inject.Singleton;
import jakarta.ws.rs.core.SecurityContext;
import java.util.function.Function;
import org.glassfish.jersey.server.ContainerRequest;
import org.glassfish.jersey.server.model.Parameter;
import org.glassfish.jersey.server.spi.internal.ValueParamProvider;

@Singleton
public class AuthenticatedUserParamProvider implements ValueParamProvider {

    @Override
    public Function<ContainerRequest, ?> getValueProvider(Parameter parameter) {
        if (parameter.getRawType().equals(Idul.class)
                && parameter.isAnnotationPresent(AuthenticatedUser.class)) {

            return request -> {
                SecurityContext securityContext = request.getSecurityContext();
                if (securityContext == null || securityContext.getUserPrincipal() == null) {
                    throw new AuthenticationException("Missing authentication");
                }

                UserPrincipal p = (UserPrincipal) securityContext.getUserPrincipal();
                return Idul.from(p.getName());
            };
        }
        return null;
    }

    @Override
    public PriorityType getPriority() {
        return Priority.NORMAL;
    }
}
