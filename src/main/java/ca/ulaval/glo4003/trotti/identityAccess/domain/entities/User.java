package ca.ulaval.glo4003.trotti.identityAccess.domain.entities;


import ca.ulaval.glo4003.trotti.account.domain.exceptions.AuthenticationException;
import ca.ulaval.glo4003.trotti.identityAccess.domain.values.Password;
import ca.ulaval.glo4003.trotti.identityAccess.domain.values.Permission;
import ca.ulaval.glo4003.trotti.identityAccess.domain.values.Role;
import java.util.List;

public class User {
    private final String idul;
    private final Role role;
    private final List<Permission> permission;
    private final Password password;

    public User(String idul, Role role, List<Permission> permission, Password password) {
        this.idul = idul;
        this.role = role;
        this.permission = permission;
        this.password = password;
    }

    private void verifyPassword(String rawPassword) {
        if (!this.password.matches(rawPassword)) {
            throw new AuthenticationException("Invalid email or password");
        }
    }
}
