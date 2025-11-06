package ca.ulaval.glo4003.trotti.identityAccess.application.dto;

import ca.ulaval.glo4003.trotti.identityAccess.domain.values.Password;
import ca.ulaval.glo4003.trotti.identityAccess.domain.values.Permission;
import ca.ulaval.glo4003.trotti.identityAccess.domain.values.Role;
import java.util.List;

public class JwtPrincipal {
    private String idul;
    private final Role role;
    private final List<Permission> permissions;
    private final Password hashedPassword;

    public JwtPrincipal(String idul, Role role, List<Permission> permissions, Password hashedPassword) {
        this.idul = idul;
        this.role = role;
        this.permissions = permissions;
        this.hashedPassword = hashedPassword;
    }

    public JwtPrincipal(String idul, Role role, List<Permission> permissions){
        this(idul, role, permissions, null);
    }
}