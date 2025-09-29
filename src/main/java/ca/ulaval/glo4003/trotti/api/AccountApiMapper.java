package ca.ulaval.glo4003.trotti.api;

import ca.ulaval.glo4003.trotti.api.dto.requests.CreateAccountRequest;
import ca.ulaval.glo4003.trotti.application.account.dto.AccountDto;
import ca.ulaval.glo4003.trotti.domain.account.Gender;
import ca.ulaval.glo4003.trotti.domain.account.Idul;
import ca.ulaval.glo4003.trotti.domain.account.Password;
import ca.ulaval.glo4003.trotti.domain.account.PasswordHasher;
import ca.ulaval.glo4003.trotti.domain.commons.Email;

public class AccountApiMapper {
    private final PasswordHasher hasher;

    public AccountApiMapper(PasswordHasher hasher) {
        this.hasher = hasher;
    }

    public AccountDto toAccountDto(CreateAccountRequest request) {
        Gender gender = Gender.fromString(request.gender());
        Idul idul = Idul.from(request.idul());
        Email email = Email.from(request.email());
        Password password = Password.fromPlain(request.password(), hasher);

        return new AccountDto(request.name(), request.birthDate(), gender, idul, email, password);
    }
}
