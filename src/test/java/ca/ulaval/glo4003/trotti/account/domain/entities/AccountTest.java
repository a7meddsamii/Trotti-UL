package ca.ulaval.glo4003.trotti.account.domain.entities;

import ca.ulaval.glo4003.trotti.account.domain.values.Password;
import ca.ulaval.glo4003.trotti.account.domain.values.Role;
import ca.ulaval.glo4003.trotti.account.domain.values.permissions.CartPermissions;
import ca.ulaval.glo4003.trotti.account.domain.values.permissions.MaintenancePermissions;
import ca.ulaval.glo4003.trotti.account.domain.values.permissions.TripPermissions;
import ca.ulaval.glo4003.trotti.account.fixtures.AccountFixture;
import java.time.LocalDate;
import java.time.Month;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class AccountTest {

    private static final LocalDate A_GIVEN_BIRTHDATE = LocalDate.of(2001, Month.FEBRUARY, 10);
    private static final int EXPECTED_AGE = 24;
    private static final String VALID_RAW_PASSWORD = "ValidPass123!";
    private static final String INVALID_RAW_PASSWORD = "WrongPassword!";

    private Password mockPassword;
    private Account account;

    @BeforeEach
    void setup() {
        mockPassword = Mockito.mock(Password.class);
        account = createAccountWithMockPassword();
    }

    @Test
    void givenBirthDate_whenGetAge_thenReturnCorrectAge() {
        account = new AccountFixture().withBirthDate(A_GIVEN_BIRTHDATE).build();

        Assertions.assertEquals(EXPECTED_AGE, account.getAge());
    }

    @Test
    void givenMatchingPassword_whenVerifyPassword_thenReturnTrue() {
        Mockito.when(mockPassword.matches(VALID_RAW_PASSWORD)).thenReturn(true);

        boolean match = account.verifyPassword(VALID_RAW_PASSWORD);

        Assertions.assertTrue(match);
    }

    @Test
    void givenNonMatchingPassword_whenVerifyPassword_thenReturnFalse() {
        Mockito.when(mockPassword.matches(INVALID_RAW_PASSWORD)).thenReturn(false);

        boolean match = account.verifyPassword(INVALID_RAW_PASSWORD);

        Assertions.assertFalse(match);
    }

    @Test
    void givenPassword_whenVerifyPasswordMatches_thenPasswordMatchIsCalled() {
        Mockito.when(mockPassword.matches(VALID_RAW_PASSWORD)).thenReturn(true);

        account.verifyPassword(VALID_RAW_PASSWORD);

        Mockito.verify(mockPassword).matches(VALID_RAW_PASSWORD);
    }

    @Test
    void givenAccount_whenAssignNewRole_thenRoleChanges() {
        Account account = new AccountFixture().withRole(Role.USER).build();

        account.assignRole(Role.EMPLOYEE);

        Assertions.assertEquals(Role.EMPLOYEE, account.getRole());
    }

    @Test
    void givenNullRole_whenAssignRole_thenThrowsNullPointerException() {
        Account account = new AccountFixture().withRole(Role.USER).build();

        Assertions.assertThrows(NullPointerException.class, () -> account.assignRole(null));
    }

    @Test
    void givenUserAccount_whenCheckPermission_thenDelegatesToRole() {
        Account student = new AccountFixture().withRole(Role.USER).build();

        boolean hasPermission = student.hasPermission(CartPermissions.CART_MODIFICATION);

        Assertions.assertTrue(hasPermission);
    }

    @Test
    void givenEmployeeAccount_whenCheckPermissionNotInRole_thenReturnsFalse() {
        Account employee = new AccountFixture().withRole(Role.EMPLOYEE).build();

        boolean hasPermission = employee.hasPermission(CartPermissions.CART_MODIFICATION);

        Assertions.assertFalse(hasPermission);
    }

    @Test
    void givenAccountWithRoleChange_whenCheckPermissions_thenPermissionsChangeAccordingly() {
        Account account = new AccountFixture().withRole(Role.USER).build();
        Assertions.assertTrue(account.hasPermission(CartPermissions.CART_MODIFICATION));

        account.assignRole(Role.EMPLOYEE);

        Assertions.assertFalse(account.hasPermission(CartPermissions.CART_MODIFICATION));
        Assertions.assertTrue(account.hasPermission(TripPermissions.MAKE_TRIP));
    }


    private Account createAccountWithMockPassword() {
        return new Account(AccountFixture.A_NAME, AccountFixture.A_BIRTHDATE,
                AccountFixture.A_GENDER, AccountFixture.AN_IDUL, AccountFixture.AN_EMAIL,
                mockPassword, AccountFixture.A_ROLE);
    }
}
