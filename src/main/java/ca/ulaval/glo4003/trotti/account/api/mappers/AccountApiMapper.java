package ca.ulaval.glo4003.trotti.account.api.mappers;

import ca.ulaval.glo4003.trotti.account.api.dto.CreateAccountRequest;
import ca.ulaval.glo4003.trotti.account.application.dto.RegistrationDto;
import ca.ulaval.glo4003.trotti.account.domain.values.*;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import java.time.LocalDate;

public class AccountApiMapper {

    public AccountApiMapper() {}

    public RegistrationDto toPasswordRegistrationDto(CreateAccountRequest request) {
        Gender gender = Gender.fromString(request.gender());
        Idul idul = Idul.from(request.idul());
        Email email = Email.from(request.email());
        String password = request.password();
        LocalDate birthDate = request.birthDate();
        Role role = Role.fromString(request.role());

        return new RegistrationDto(request.name(), birthDate, gender, idul, email, password, role);
    }
}
