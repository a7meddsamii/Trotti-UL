package ca.ulaval.glo4003.trotti.domain.authentication.services;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.authentication.values.AuthenticationToken;

public interface AuthenticationService {
    AuthenticationToken generateToken(Idul accountId);

    Idul authenticate(AuthenticationToken token);

    void confirmStudent(Idul idul);
}
