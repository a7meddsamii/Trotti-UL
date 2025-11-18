package ca.ulaval.glo4003.trotti.account.infrastructure.security.authentication.jwtsecuritycontext;

import ca.ulaval.glo4003.trotti.account.domain.values.Permission;
import ca.ulaval.glo4003.trotti.account.domain.values.Role;
import ca.ulaval.glo4003.trotti.account.infrastructure.security.authentication.UserPrincipal;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import java.util.Set;

public class JwtPrincipal implements UserPrincipal {
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

    @Override
    public Role getRole() {
        return this.role;
    }

    @Override
    public Set<Permission> getPermissions() {
        return this.permissions;
    }
}
