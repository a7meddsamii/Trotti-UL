package ca.ulaval.glo4003.trotti.domain.authentication;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;

public interface AuthenticationService {
    AuthenticationToken generateToken(Idul accountId);

    Idul authenticate(AuthenticationToken token);
	
	void confirmStudent(Idul idul);
}
