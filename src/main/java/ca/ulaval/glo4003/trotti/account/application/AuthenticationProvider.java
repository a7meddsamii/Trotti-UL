package ca.ulaval.glo4003.trotti.account.application;

import ca.ulaval.glo4003.trotti.account.application.dto.AccountDto;
import ca.ulaval.glo4003.trotti.account.application.dto.LoginDto;
import ca.ulaval.glo4003.trotti.account.application.dto.RegistrationDto;
import ca.ulaval.glo4003.trotti.account.domain.values.Email;

public interface AuthenticationProvider {
    Email verify(LoginDto loginDto);

    AccountDto register(RegistrationDto RegistrationDto);
}
