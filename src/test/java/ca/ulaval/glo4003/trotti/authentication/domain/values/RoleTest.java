package ca.ulaval.glo4003.trotti.authentication.domain.values;

import ca.ulaval.glo4003.trotti.authentication.domain.exception.AuthenticationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class RoleTest {
    private static final String VALID_ROLE_NAME = "STUDENT";
    private static final String INVALID_ROLE_NAME = "INVALID_ROLE";

    @Test
    public void givenValidRole_whenValidating_thenReturnCorrectRole() {
        Role role = Role.fromString(VALID_ROLE_NAME);

        Assertions.assertEquals(Role.STUDENT, role);
    }

    @Test
    public void givenInvalidRole_whenValidating_thenThrowsAuthenticationException() {
        Executable executable = () -> Role.fromString(INVALID_ROLE_NAME);

        Assertions.assertThrows(AuthenticationException.class, executable);
    }

}
