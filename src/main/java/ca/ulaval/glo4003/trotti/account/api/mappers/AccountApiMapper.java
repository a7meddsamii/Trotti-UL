package ca.ulaval.glo4003.trotti.account.api.mappers;

import ca.ulaval.glo4003.trotti.account.api.dto.CreateAccountRequest;
import ca.ulaval.glo4003.trotti.account.application.dto.AccountDto;
import ca.ulaval.glo4003.trotti.account.application.dto.PasswordRegistrationDto;
import ca.ulaval.glo4003.trotti.account.domain.services.PasswordHasher;
import ca.ulaval.glo4003.trotti.account.domain.values.*;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.commons.domain.exceptions.InvalidParameterException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class AccountApiMapper {

    public AccountApiMapper() {}

    public PasswordRegistrationDto toPasswordRegistrationDto(CreateAccountRequest request) {
        if (request == null) {
            throw new InvalidParameterException("Provide the information to create an account");
        }

        Gender gender = Gender.fromString(request.gender());
        Idul idul = Idul.from(request.idul());
        Email email = Email.from(request.email());
        String password = request.password();
        LocalDate birthDate = parseDate(request.birthDate());
        Role role = Role.fromString(request.role());

        return new PasswordRegistrationDto(request.name(), birthDate, gender, idul, email, password, role);
    }

    private LocalDate parseDate(String birthDateString) {
        try {
            return LocalDate.parse(birthDateString);
        } catch (DateTimeParseException e) {
            throw new InvalidParameterException("Invalid birthDate format, expected yyyy-MM-dd");
        }
    }
}
