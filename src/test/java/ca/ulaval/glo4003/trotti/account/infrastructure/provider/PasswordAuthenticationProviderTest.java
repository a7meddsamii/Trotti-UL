package ca.ulaval.glo4003.trotti.account.infrastructure.provider;

import ca.ulaval.glo4003.trotti.account.application.dto.AccountDto;
import ca.ulaval.glo4003.trotti.account.application.dto.LoginDto;
import ca.ulaval.glo4003.trotti.account.application.dto.RegistrationDto;
import ca.ulaval.glo4003.trotti.account.domain.exceptions.AuthenticationException;
import ca.ulaval.glo4003.trotti.account.domain.services.PasswordHasher;
import ca.ulaval.glo4003.trotti.account.domain.values.Email;
import ca.ulaval.glo4003.trotti.account.domain.values.Gender;
import ca.ulaval.glo4003.trotti.account.domain.values.Role;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.commons.domain.exceptions.InvalidParameterException;
import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

class PasswordAuthenticationProviderTest {
    private static final Email EMAIL = Email.from("user@ulaval.ca");
    private static final String A_NAME = "john";
    private static final Idul AN_IDUL = Idul.from("jhn0");
    private static final Role A_ROLE = Role.STUDENT;
    private static final LocalDate A_BIRTHDATE = LocalDate.of(1990, 1, 1);
    private static final String A_VALID_PASSWORD = "Password1!";
    private static final String NON_MATCHING_PASSWORD = "NON-MATCHING1.";
    private static final String AN_INVALID_PASSWORD = "weak";
    private static final String A_HASHED_PASSWORD = "hashed";
    private static final Gender A_GENDER = Gender.MALE;

    private PasswordHasher passwordHasher;
    private PasswordAuthenticationProvider provider;

    @BeforeEach
    void setup() {
        passwordHasher = Mockito.mock(PasswordHasher.class);

        provider = new PasswordAuthenticationProvider(passwordHasher);
    }

    @Test
    void givenValidPassword_whenRegister_thenVerifyReturnsEmail() {
        RegistrationDto registration = createValidRegistrationDto();
        Mockito.when(passwordHasher.hash(A_VALID_PASSWORD)).thenReturn(A_HASHED_PASSWORD);
        Mockito.when(passwordHasher.matches(A_VALID_PASSWORD, A_HASHED_PASSWORD)).thenReturn(true);
        provider.register(registration);
        LoginDto loginDto = new LoginDto(EMAIL, A_VALID_PASSWORD);

        Email result = provider.verify(loginDto);

        Assertions.assertEquals(EMAIL, result);
    }

    @Test
    void givenInvalidPassword_whenRegister_thenThrowsInvalidParameterException() {
        RegistrationDto registration = new RegistrationDto(A_NAME, A_BIRTHDATE, A_GENDER, AN_IDUL,
                EMAIL, AN_INVALID_PASSWORD, A_ROLE);

        Executable executable = () -> provider.register(registration);

        Assertions.assertThrows(InvalidParameterException.class, executable);
    }

    @Test
    void givenNonExistentEmail_whenVerify_thenThrowsAuthenticationException() {
        LoginDto dto = new LoginDto(EMAIL, A_VALID_PASSWORD);

        Executable executable = () -> provider.verify(dto);

        Assertions.assertThrows(AuthenticationException.class, executable);
    }

    @Test
    void givenExistingEmailWithMatchingPassword_whenVerify_thenReturnsEmail() {
        RegistrationDto registration = createValidRegistrationDto();
        Mockito.when(passwordHasher.hash(A_VALID_PASSWORD)).thenReturn(A_HASHED_PASSWORD);
        Mockito.when(passwordHasher.matches(A_VALID_PASSWORD, A_HASHED_PASSWORD)).thenReturn(true);
        provider.register(registration);
        LoginDto login = new LoginDto(EMAIL, A_VALID_PASSWORD);

        Email result = provider.verify(login);

        Assertions.assertEquals(EMAIL, result);
    }

    @Test
    void givenExistingEmailWithWrongPassword_whenVerify_thenThrowsAuthenticationException() {
        RegistrationDto registration = createValidRegistrationDto();
        Mockito.when(passwordHasher.hash(A_VALID_PASSWORD)).thenReturn(A_HASHED_PASSWORD);
        Mockito.when(passwordHasher.matches(NON_MATCHING_PASSWORD, A_HASHED_PASSWORD))
                .thenReturn(false);
        provider.register(registration);
        LoginDto dto = new LoginDto(EMAIL, NON_MATCHING_PASSWORD);

        Executable executable = () -> provider.verify(dto);

        Assertions.assertThrows(AuthenticationException.class, executable);
    }

    private RegistrationDto createValidRegistrationDto() {
        return new RegistrationDto(A_NAME, A_BIRTHDATE, A_GENDER, AN_IDUL, EMAIL, A_VALID_PASSWORD,
                A_ROLE);
    }

}
