package ca.ulaval.glo4003.trotti.authentication.domain.services;

import ca.ulaval.glo4003.trotti.authentication.domain.values.Permission;
import ca.ulaval.glo4003.trotti.authentication.domain.values.Role;
import ca.ulaval.glo4003.trotti.authentication.domain.values.SessionToken;
import ca.ulaval.glo4003.trotti.account.domain.values.Idul;

import java.util.Set;

public interface SessionTokenService {
	SessionToken generateToken(Idul idul, Role role, Set<Permission> permissions);
}
