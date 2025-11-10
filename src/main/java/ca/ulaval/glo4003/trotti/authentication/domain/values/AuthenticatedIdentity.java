package ca.ulaval.glo4003.trotti.authentication.domain.values;


import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import java.util.Set;

public record AuthenticatedIdentity(Idul idul, Role role, Set<Permission> permissions) {}
