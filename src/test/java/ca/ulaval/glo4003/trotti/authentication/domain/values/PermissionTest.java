package ca.ulaval.glo4003.trotti.authentication.domain.values;

import ca.ulaval.glo4003.trotti.authentication.domain.exception.AuthenticationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class PermissionTest {
    private static final String VALID_PERMISSION_NAME = "ORDER_CONFIRM";
    private static final String INVALID_PERMISSION_NAME = "INVALID_PERMISSION";

    @Test
    void givenValidPermissionName_whenFromString_thenReturnsCorrectPermission() {
        Permission permission = Permission.fromString(VALID_PERMISSION_NAME);

        Assertions.assertEquals(Permission.ORDER_CONFIRM, permission);
    }

    @Test
    void givenInvalidPermissionName_whenFromString_thenThrowsAuthenticationException() {
        Executable executable = () -> Permission.fromString(INVALID_PERMISSION_NAME);

        Assertions.assertThrows(AuthenticationException.class, executable);
    }
}
