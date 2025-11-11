package ca.ulaval.glo4003.trotti.authentication.infrastructure.security.authentication;

import ca.ulaval.glo4003.trotti.authentication.domain.values.Permission;
import ca.ulaval.glo4003.trotti.authentication.domain.values.Role;

import java.security.Principal;
import java.util.Set;

public interface UserPrincipal extends Principal {
	Role getRole();
	Set<Permission> getPermissions();
}
