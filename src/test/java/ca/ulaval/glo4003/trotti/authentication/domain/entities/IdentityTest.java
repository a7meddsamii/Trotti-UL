package ca.ulaval.glo4003.trotti.authentication.domain.entities;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.authentication.domain.exception.AuthenticationException;
import ca.ulaval.glo4003.trotti.authentication.domain.values.HashedPassword;
import ca.ulaval.glo4003.trotti.authentication.domain.values.Permission;
import ca.ulaval.glo4003.trotti.authentication.domain.values.Role;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class IdentityTest {
    private static final Idul AN_IDUL = Idul.from("JD12345");
    private static final Role A_ROLE = Role.STUDENT;
    private static final Set<Permission> PERMISSIONS =
            Set.of(Permission.REQUEST_MAINTENANCE, Permission.END_MAINTENANCE);
    private static final String A_RAW_PASSWORD = "StrongPass1!";
    private static final String MATCHING_RAW_PASSWORD = "MatchingPass1!";
    private static final String NON_MATCHING_RAW_PASSWORD = "NonMatchingPass4!";
    private HashedPassword hashedPassword;

    private Identity identity;

    @BeforeEach
    void setup() {
        hashedPassword = Mockito.mock(HashedPassword.class);
        identity = new Identity(AN_IDUL, A_ROLE, PERMISSIONS, hashedPassword);
    }

    @Test
    void givenPassword_whenVerifyPassword_thenPasswordMatchesIsCalled() {
        Mockito.when(hashedPassword.matches(A_RAW_PASSWORD)).thenReturn(true);

        identity.verifyPassword(A_RAW_PASSWORD);

        Mockito.verify(hashedPassword).matches(A_RAW_PASSWORD);
    }

    @Test
    void givenMatchingPassword_whenVerifyPassword_thenDoesNotThrowsAuthenticationException() {
        Mockito.when(hashedPassword.matches(MATCHING_RAW_PASSWORD)).thenReturn(true);

        Executable executable = () -> identity.verifyPassword(MATCHING_RAW_PASSWORD);

        Assertions.assertDoesNotThrow(executable);
    }

    @Test
    void givenNonMatchingPassword_whenVerifyPassword_thenThrowsAuthenticationException() {
        Mockito.when(hashedPassword.matches(NON_MATCHING_RAW_PASSWORD)).thenReturn(false);

        Executable executable = () -> identity.verifyPassword(NON_MATCHING_RAW_PASSWORD);

        Assertions.assertThrows(AuthenticationException.class, executable);
    }
}
