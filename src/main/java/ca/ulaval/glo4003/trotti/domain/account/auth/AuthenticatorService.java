package ca.ulaval.glo4003.trotti.domain.account.auth;

import ca.ulaval.glo4003.trotti.domain.account.Idul;

public interface AuthenticatorService {
    AuthToken generateToken(Idul accountId);

    Idul authenticate(AuthToken token);
}
