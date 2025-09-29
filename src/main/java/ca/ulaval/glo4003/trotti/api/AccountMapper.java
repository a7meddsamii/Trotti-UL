package ca.ulaval.glo4003.trotti.api;

import ca.ulaval.glo4003.trotti.api.dto.requests.CreateAccountRequest;
import ca.ulaval.glo4003.trotti.application.account.dto.AccountRegistration;
import ca.ulaval.glo4003.trotti.domain.account.*;
import java.security.InvalidParameterException;
import java.time.DateTimeException;
import java.time.LocalDate;

public class AccountMapper {
    private final PasswordHasher hasher;

    public AccountMapper(PasswordHasher hasher) {
        this.hasher = hasher;
    }

    public AccountRegistration toAccountRegistration(CreateAccountRequest request) {
        LocalDate birthDate = parseDate(request.birthDate());
        Gender gender = Gender.fromString(request.gender());
        Idul idul = Idul.from(request.idul());
        Email email = Email.from(request.email());
        Password password = Password.fromPlain(request.password(), hasher);

        return new AccountRegistration(request.name(), birthDate, gender, idul, email, password);
    }

    private LocalDate parseDate(String date) {
        try {
            return LocalDate.parse(date);
        } catch (DateTimeException e) {
            throw new InvalidParameterException("date format ");
        }
    }
}
