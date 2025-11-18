package ca.ulaval.glo4003.trotti.account.infrastructure.security.authorization;

import ca.ulaval.glo4003.trotti.account.domain.exceptions.AuthenticationException;
import ca.ulaval.glo4003.trotti.account.domain.values.Permission;
import ca.ulaval.glo4003.trotti.account.infrastructure.security.authentication.UserPrincipal;
import jakarta.annotation.Priority;
import jakarta.annotation.security.DenyAll;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.ext.Provider;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

import static ca.ulaval.glo4003.trotti.trip.infrastructure.config.scheduler.ServerLifeCycleListener.LOGGER;

@Provider
@Priority(Priorities.AUTHORIZATION)
public class AuthorizationFilter implements ContainerRequestFilter {
    @Context
    private ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext requestContext) {
        AnnotatedElement methodCalled = resourceInfo.getResourceMethod();
        AnnotatedElement resourceClass = resourceInfo.getResourceClass();
		
		LOGGER.warn("#### checking user permissions #####");

        if (isAnnotated(methodCalled, PermitAll.class)
                || isAnnotated(resourceClass, PermitAll.class)) {
            return;
        }

        if (isAnnotated(methodCalled, DenyAll.class) || isAnnotated(resourceClass, DenyAll.class)) {
            throw new ForbiddenException("Access denied");
        }
		
		LOGGER.warn("#### validating user permissions #####");

        SecurityContext securityContext = requestContext.getSecurityContext();
        UserPrincipal user = extractPrincipal(securityContext);
        validateRole(methodCalled, resourceClass, securityContext);
        validatePermissions(methodCalled, resourceClass, user);
    }

    private UserPrincipal extractPrincipal(SecurityContext securityContext) {
        if (securityContext == null || securityContext.getUserPrincipal() == null) {
            throw new AuthenticationException("Bearer realm=\"api\"");
        }

        return (UserPrincipal) securityContext.getUserPrincipal();
    }

    private void validateRole(AnnotatedElement methodCalled, AnnotatedElement resourceClass,
            SecurityContext securityContext) {
        RolesAllowed allowedRoles =
                firstNonNullAnnotation(methodCalled.getAnnotation(RolesAllowed.class),
                        resourceClass.getAnnotation(RolesAllowed.class));

        if (allowedRoles != null) {
            boolean ok =
                    Arrays.stream(allowedRoles.value()).anyMatch(securityContext::isUserInRole);
            if (!ok) {
                throw new ForbiddenException("Role required");
            }
        }
    }

    private void validatePermissions(AnnotatedElement methodCalled, AnnotatedElement resourceClass,
            UserPrincipal user) {
        RequiresPermissions requiredPermissions =
                firstNonNullAnnotation(methodCalled.getAnnotation(RequiresPermissions.class),
                        resourceClass.getAnnotation(RequiresPermissions.class));

        if (requiredPermissions != null) {
            Set<Permission> userPerms = user.getPermissions();
            Permission[] needed = requiredPermissions.value();
            boolean ok;

            if (Objects.requireNonNull(requiredPermissions.logical()) == Logical.ANY) {
                ok = Arrays.stream(needed).anyMatch(userPerms::contains);
            } else {
                ok = Arrays.stream(needed).allMatch(userPerms::contains);
            }

            if (!ok) {
                throw new ForbiddenException("Permission required");
            }
        }
    }

    private boolean isAnnotated(AnnotatedElement element, Class<? extends Annotation> annotation) {
        return element != null && element.isAnnotationPresent(annotation);
    }

    private <T> T firstNonNullAnnotation(T a, T b) {
        T result = a;
        if (result == null) {
            result = b;
        }

        return result;
    }
}
