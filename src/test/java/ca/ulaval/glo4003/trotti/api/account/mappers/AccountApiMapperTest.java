package ca.ulaval.glo4003.trotti.api.account.mappers;

import ca.ulaval.glo4003.trotti.api.account.dto.request.CreateAccountRequest;
import ca.ulaval.glo4003.trotti.api.account.mappers.AccountApiMapper;
import ca.ulaval.glo4003.trotti.application.account.dto.AccountDto;
import ca.ulaval.glo4003.trotti.domain.account.services.PasswordHasher;
import ca.ulaval.glo4003.trotti.domain.commons.exceptions.InvalidParameterException;
import ca.ulaval.glo4003.trotti.fixtures.AccountFixture;
import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

class AccountApiMapperTest {
    public static final String HASHED_PASSWORD = "hashed-password";
    public static final String AN_INVALID_BIRTHDATE_FORMAT = "not-a-date";
    private static final String A_WRONG_FORMATTED_BIRTHDATE = "2000/01/01";

    private PasswordHasher passwordHasher;
    private AccountApiMapper accountApiMapper;

    @BeforeEach
    void setup() {
        passwordHasher = Mockito.mock(PasswordHasher.class);
        accountApiMapper = new AccountApiMapper(passwordHasher);
    }

    @Test
    void givenValidRequest_whenToAccountDto_thenReturnFullyMappedAccountDto() {
        CreateAccountRequest request = buildValidRequest();
        Mockito.when(passwordHasher.hash(AccountFixture.A_RAW_PASSWORD))
                .thenReturn(HASHED_PASSWORD);

        AccountDto dto = accountApiMapper.toAccountDto(request);

        Assertions.assertEquals(request.name(), dto.name());
        Assertions.assertEquals(LocalDate.parse(request.birthDate()), dto.birthDate());
        Assertions.assertEquals(request.gender(), dto.gender().name());
        Assertions.assertEquals(request.idul(), dto.idul().toString());
        Assertions.assertEquals(request.email(), dto.email().toString());
        Assertions.assertEquals(HASHED_PASSWORD, dto.password().toString());

    }

    @Test
    void givenInvalidDateFormat_whenToAccountDto_thenThrowsInvalidParameterException() {
        CreateAccountRequest request =
                new CreateAccountRequest(AccountFixture.A_NAME, AN_INVALID_BIRTHDATE_FORMAT,
                        AccountFixture.A_GENDER_STRING, AccountFixture.AN_IDUL_STRING,
                        AccountFixture.AN_EMAIL_STRING, AccountFixture.A_RAW_PASSWORD);

        Executable toAccountDtoExecutable = () -> accountApiMapper.toAccountDto(request);

        Assertions.assertThrows(InvalidParameterException.class, toAccountDtoExecutable);
    }

    @Test
    void givenWrongFormattedBirthDate_whenToAccountDto_thenThrowsInvalidParameterException() {
        CreateAccountRequest request =
                new CreateAccountRequest(AccountFixture.A_NAME, A_WRONG_FORMATTED_BIRTHDATE,
                        AccountFixture.A_GENDER_STRING, AccountFixture.AN_IDUL_STRING,
                        AccountFixture.AN_EMAIL_STRING, AccountFixture.A_RAW_PASSWORD);

        Executable executable = () -> accountApiMapper.toAccountDto(request);

        Assertions.assertThrows(InvalidParameterException.class, executable);
    }

    private CreateAccountRequest buildValidRequest() {
        return new CreateAccountRequest(AccountFixture.A_NAME, AccountFixture.A_STRING_BIRTHDATE,
                AccountFixture.A_GENDER_STRING, AccountFixture.AN_IDUL_STRING,
                AccountFixture.AN_EMAIL_STRING, AccountFixture.A_RAW_PASSWORD);
    }
}
