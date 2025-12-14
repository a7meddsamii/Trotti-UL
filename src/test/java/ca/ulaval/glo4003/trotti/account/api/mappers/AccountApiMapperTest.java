package ca.ulaval.glo4003.trotti.account.api.mappers;

import ca.ulaval.glo4003.trotti.account.api.dto.CreateAccountRequest;
import ca.ulaval.glo4003.trotti.account.application.dto.RegistrationDto;
import ca.ulaval.glo4003.trotti.account.domain.services.PasswordHasher;
import ca.ulaval.glo4003.trotti.account.fixtures.AccountFixture;
import ca.ulaval.glo4003.trotti.commons.domain.exceptions.InvalidParameterException;
import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

class AccountApiMapperTest {
    public static final String HASHED_PASSWORD = "hashed-password";
    public static final String INVALID_BIRTHDATE_FORMAT = "not-a-date";
    private static final String WRONG_FORMATTED_BIRTHDATE = "2000/01/01";

    private PasswordHasher passwordHasher;
    private AccountApiMapper accountApiMapper;

    @BeforeEach
    void setup() {
        passwordHasher = Mockito.mock(PasswordHasher.class);
        accountApiMapper = new AccountApiMapper();
    }

    @Test
    void givenValidRequest_whenToAccountDto_thenReturnFullyMappedAccountDto() {
        CreateAccountRequest request = buildValidRequest();
        Mockito.when(passwordHasher.hash(AccountFixture.RAW_PASSWORD))
                .thenReturn(HASHED_PASSWORD);

        RegistrationDto dto = accountApiMapper.toPasswordRegistrationDto(request);

        Assertions.assertEquals(request.name(), dto.name());
        Assertions.assertEquals(LocalDate.parse(request.birthDate()), dto.birthDate());
        Assertions.assertEquals(request.gender(), dto.gender().name());
        Assertions.assertEquals(request.idul(), dto.idul().toString());
        Assertions.assertEquals(request.email(), dto.email().toString());

    }

    @Test
    void givenInvalidDateFormat_whenToAccountDto_thenThrowsInvalidParameterException() {
        CreateAccountRequest request = new CreateAccountRequest(AccountFixture.NAME,
                INVALID_BIRTHDATE_FORMAT, AccountFixture.GENDER_STRING,
                AccountFixture.IDUL_STRING, AccountFixture.EMAIL_STRING,
                AccountFixture.RAW_PASSWORD, AccountFixture.ROLE_STRING);

        Executable toAccountDtoExecutable =
                () -> accountApiMapper.toPasswordRegistrationDto(request);

        Assertions.assertThrows(InvalidParameterException.class, toAccountDtoExecutable);
    }

    @Test
    void givenWrongFormattedBirthDate_whenToAccountDto_thenThrowsInvalidParameterException() {
        CreateAccountRequest request = new CreateAccountRequest(AccountFixture.NAME,
                WRONG_FORMATTED_BIRTHDATE, AccountFixture.GENDER_STRING,
                AccountFixture.IDUL_STRING, AccountFixture.EMAIL_STRING,
                AccountFixture.RAW_PASSWORD, AccountFixture.ROLE_STRING);

        Executable executable = () -> accountApiMapper.toPasswordRegistrationDto(request);

        Assertions.assertThrows(InvalidParameterException.class, executable);
    }

    private CreateAccountRequest buildValidRequest() {
        return new CreateAccountRequest(AccountFixture.NAME, AccountFixture.STRING_BIRTHDATE,
                AccountFixture.GENDER_STRING, AccountFixture.IDUL_STRING,
                AccountFixture.EMAIL_STRING, AccountFixture.RAW_PASSWORD,
                AccountFixture.ROLE_STRING);
    }
}
