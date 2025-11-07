package ca.ulaval.glo4003.trotti.authentication.domain.entities;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.authentication.domain.exception.AuthenticationException;
import ca.ulaval.glo4003.trotti.authentication.domain.values.Permission;
import ca.ulaval.glo4003.trotti.authentication.domain.values.Role;
import ca.ulaval.glo4003.trotti.commons.domain.Password;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class IdentityTest {
    private static final Idul AN_IDUL = Idul.from("JD12345");
    private static final Role A_ROLE = Role.STUDENT;
    private static final Set<Permission> PERMISSIONS =
            Set.of(Permission.REQUEST_MAINTENANCE, Permission.END_MAINTENANCE);
    private static final String A_RAW_PASSWORD = "StrongPass1!";
    private static final String MATCHING_RAW_PASSWORD = "MatchingPass1!";
    private static final String NON_MATCHING_RAW_PASSWORD = "NonMatchingPass4!";
    private Password password;

    @BeforeEach
    void setup() {
        password = Mockito.mock(Password.class);
    }

    @Test
    void givenPassword_whenVerifyPassword_thenPasswordMatchesIsCalled() {
        Mockito.when(password.matches(A_RAW_PASSWORD)).thenReturn(true);
        Identity identity = createIdentityWithMockPassword();

        identity.verifyPassword(A_RAW_PASSWORD);

        Mockito.verify(password).matches(A_RAW_PASSWORD);
    }

    @Test
    void givenMatchingPassword_whenVerifyPassword_thenDoesNotThrowsAuthenticationException() {
        Mockito.when(password.matches(MATCHING_RAW_PASSWORD)).thenReturn(true);

        Identity identity = createIdentityWithMockPassword();

        Assertions.assertDoesNotThrow(() -> identity.verifyPassword(MATCHING_RAW_PASSWORD));
    }

    @Test
    void givenNonMatchingPassword_whenVerifyPassword_thenThrowsAuthenticationException() {
        Mockito.when(password.matches(NON_MATCHING_RAW_PASSWORD)).thenReturn(false);

        Identity identity = createIdentityWithMockPassword();

        Assertions.assertThrows(AuthenticationException.class,
                () -> identity.verifyPassword(NON_MATCHING_RAW_PASSWORD));
    }

    private Identity createIdentityWithMockPassword() {
        return new Identity(AN_IDUL, A_ROLE, PERMISSIONS, password);
    }
}
