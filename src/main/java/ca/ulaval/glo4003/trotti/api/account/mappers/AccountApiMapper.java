package ca.ulaval.glo4003.trotti.api.account.mappers;

import ca.ulaval.glo4003.trotti.api.account.dto.request.CreateAccountRequest;
import ca.ulaval.glo4003.trotti.application.account.dto.AccountDto;
import ca.ulaval.glo4003.trotti.domain.account.services.PasswordHasher;
import ca.ulaval.glo4003.trotti.domain.account.values.Email;
import ca.ulaval.glo4003.trotti.domain.account.values.Gender;
import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.account.values.Password;
import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

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
        LocalDate birthDate = parseDate(request.birthDate());

        return new AccountDto(request.name(), birthDate, gender, idul, email, password);
    }

    private LocalDate parseDate(String birthDateString) {
        try {
            return LocalDate.parse(birthDateString);
        } catch (DateTimeParseException e) {
            throw new InvalidParameterException("Invalid birthDate format, expected yyyy-MM-dd", e);
        }
    }
}
