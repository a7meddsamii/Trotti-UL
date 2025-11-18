package ca.ulaval.glo4003.trotti.account.domain.services;

import ca.ulaval.glo4003.trotti.account.domain.values.AuthenticatedIdentity;
import ca.ulaval.glo4003.trotti.account.domain.values.Permission;
import ca.ulaval.glo4003.trotti.account.domain.values.Role;
import ca.ulaval.glo4003.trotti.account.domain.values.SessionToken;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import java.util.Set;

public interface SessionTokenProvider {
    SessionToken generateToken(Idul idul, Role role, Set<Permission> permissions);

    AuthenticatedIdentity deserialize(SessionToken sessionToken);
}
