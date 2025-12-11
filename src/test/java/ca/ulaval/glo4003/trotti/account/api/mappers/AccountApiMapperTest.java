package ca.ulaval.glo4003.trotti.account.api.mappers;

import ca.ulaval.glo4003.trotti.account.api.dto.CreateAccountRequest;
import ca.ulaval.glo4003.trotti.account.application.dto.RegistrationDto;
import ca.ulaval.glo4003.trotti.account.fixtures.CreateAccountRequestFixture;
import ca.ulaval.glo4003.trotti.commons.domain.exceptions.InvalidParameterException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class AccountApiMapperTest {

    private static final String INVALID_BIRTHDATE = "not-a-date";
    private static final String WRONG_FORMATTED_BIRTHDATE = "2000/01/01";

    private AccountApiMapper accountApiMapper;

    @BeforeEach
    void setup() {
        accountApiMapper = new AccountApiMapper();
    }

    @Test
    void givenInvalidDateFormat_whenToPasswordRegistrationDto_thenThrowsInvalidParameterException() {
        CreateAccountRequest request = new CreateAccountRequestFixture()
                .withBirthDate(INVALID_BIRTHDATE)
                .build();

        Executable toPasswordRegistrationDto = () -> accountApiMapper.toPasswordRegistrationDto(request);

        Assertions.assertThrows(InvalidParameterException.class, toPasswordRegistrationDto);
    }

    @Test
    void givenWrongFormattedBirthDate_whenToPasswordRegistrationDto_thenThrowsInvalidParameterException() {
        CreateAccountRequest request = new CreateAccountRequestFixture()
                .withBirthDate(WRONG_FORMATTED_BIRTHDATE)
                .build();

        Executable toPasswordRegistrationDto = () -> accountApiMapper.toPasswordRegistrationDto(request);

        Assertions.assertThrows(InvalidParameterException.class, toPasswordRegistrationDto);
    }

    @Test
    void givenNullRequest_whenToPasswordRegistrationDto_thenThrowsInvalidParameterException() {
        Executable toPasswordRegistrationDto = () -> accountApiMapper.toPasswordRegistrationDto(null);

        Assertions.assertThrows(InvalidParameterException.class, toPasswordRegistrationDto);
    }
}
