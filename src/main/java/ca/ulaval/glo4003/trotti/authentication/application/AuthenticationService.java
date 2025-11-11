package ca.ulaval.glo4003.trotti.authentication.application;

import ca.ulaval.glo4003.trotti.authentication.application.dto.LoginInfo;
import ca.ulaval.glo4003.trotti.authentication.domain.values.SessionToken;

public interface AuthenticationService {
	SessionToken login(LoginInfo loginInfo);
}
