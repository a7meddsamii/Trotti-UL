package ca.ulaval.glo4003.trotti.authentication.infrastructure.security.jwtsecuritycontext;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.authentication.domain.values.Permission;
import ca.ulaval.glo4003.trotti.authentication.domain.values.Role;
import java.security.Principal;
import java.util.Set;

public class JwtPrincipal implements Principal {
    private final Idul idul;
    private final Role role;
    private final Set<Permission> permissions;

    public JwtPrincipal(Idul idul, Role role, Set<Permission> permissions) {
        this.idul = idul;
        this.role = role;
        this.permissions = permissions;
    }


    @Override
    public String getName() {
        return this.idul.toString();
    }

    public Role getRole() {
        return this.role;
    }

    public Set<Permission> getPermissions() {
        return this.permissions;
    }
}
