package ca.ulaval.glo4003.trotti.domain.account.auth;

import ca.ulaval.glo4003.trotti.domain.account.Idul;

public interface Authenticator {
    AuthToken generateToken(Idul accountId);

    Idul authenticate(AuthToken token);
}
