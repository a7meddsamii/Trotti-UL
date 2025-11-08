package ca.ulaval.glo4003.trotti.authentication.domain.entities;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.authentication.domain.exception.AuthenticationException;
import ca.ulaval.glo4003.trotti.authentication.domain.values.Permission;
import ca.ulaval.glo4003.trotti.authentication.domain.values.Role;
import ca.ulaval.glo4003.trotti.commons.domain.Password;
import java.util.Collections;
import java.util.Set;

public class Identity {
    private final Idul idul;
    private final Role role;
    private final Set<Permission> permissions;
    private final Password password;

    public Identity(Idul idul, Role role, Set<Permission> permissions, Password password) {
        this.idul = idul;
        this.role = role;
        this.permissions = permissions;
        this.password = password;
    }

    public void verifyPassword(String rawPassword) {
        if (!this.password.matches(rawPassword)) {
            throw new AuthenticationException("Invalid email or password");
        }
    }

    public Idul getIdul() {
        return idul;
    }

    public Role getRole() {
        return role;
    }

    public Set<Permission> getPermissions() {
        return Collections.unmodifiableSet(permissions);
    }
}
