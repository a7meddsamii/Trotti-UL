package ca.ulaval.glo4003.trotti.account.domain.services;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.account.domain.values.AuthenticationToken;

public interface AuthenticationService {
    AuthenticationToken generateToken(Idul accountId);

    Idul authenticate(AuthenticationToken token);

    void confirmStudent(Idul idul);
}
