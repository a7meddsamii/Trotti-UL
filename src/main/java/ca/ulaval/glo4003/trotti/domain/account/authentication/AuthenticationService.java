package ca.ulaval.glo4003.trotti.domain.account.authentication;

import ca.ulaval.glo4003.trotti.domain.account.Idul;

public interface AuthenticationService {
    AuthenticationToken generateToken(Idul accountId);

    Idul authenticate(AuthenticationToken token);
}
