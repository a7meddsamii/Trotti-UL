package ca.ulaval.glo4003.trotti.authentication.domain.entities;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.authentication.domain.exception.AuthenticationException;
import ca.ulaval.glo4003.trotti.authentication.domain.values.HashedPassword;
import ca.ulaval.glo4003.trotti.authentication.domain.values.Permission;
import ca.ulaval.glo4003.trotti.authentication.domain.values.Role;
import java.util.Collections;
import java.util.Set;

public class Identity {
    private final Idul idul;
    private final Role role;
    private final Set<Permission> permissions;
    private final HashedPassword hashedPassword;

    public Identity(
            Idul idul,
            Role role,
            Set<Permission> permissions,
            HashedPassword hashedPassword) {
        this.idul = idul;
        this.role = role;
        this.permissions = permissions;
        this.hashedPassword = hashedPassword;
    }

    public void verifyPassword(String rawPassword) {
        if (!this.hashedPassword.matches(rawPassword)) {
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
