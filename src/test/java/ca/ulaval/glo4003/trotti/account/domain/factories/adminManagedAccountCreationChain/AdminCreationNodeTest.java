package ca.ulaval.glo4003.trotti.account.domain.factories.adminManagedAccountCreationChain;

import ca.ulaval.glo4003.trotti.account.domain.entities.Account;
import ca.ulaval.glo4003.trotti.account.domain.exceptions.AuthorizationException;
import ca.ulaval.glo4003.trotti.account.domain.values.*;
import ca.ulaval.glo4003.trotti.account.domain.values.Permission;
import ca.ulaval.glo4003.trotti.account.fixtures.AccountFixture;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import java.time.LocalDate;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

class AdminCreationNodeTest {

    private static final String A_NAME = AccountFixture.A_NAME;
    private static final LocalDate A_BIRTHDATE = AccountFixture.A_BIRTHDATE;
    private static final Gender A_GENDER = AccountFixture.A_GENDER;
    private static final Idul AN_IDUL = AccountFixture.AN_IDUL;
    private static final Email A_EMAIL = AccountFixture.AN_EMAIL;
    private static final Role NOT_ADMIN_ROLE = Role.EMPLOYEE;

    private Set<Permission> availablePermissions;
    private AdminManagedAccountCreationNode nextNode;
    private Role role;
    private AdminCreationNode adminCreationNode;

    @BeforeEach
    void setup() {
        role = Role.ADMIN;
        availablePermissions = Mockito.mock(Set.class);
        nextNode = Mockito.mock(AdminManagedAccountCreationNode.class);

        adminCreationNode = new AdminCreationNode();
        adminCreationNode.setNext(nextNode);
    }

    @Test
    void givenAdminRoleAndCorrectPermissions_whenCreateAdminManagedAccount_thenAdminAccountIsCreated() {
        Mockito.when(availablePermissions.contains(Mockito.any(Permission.class))).thenReturn(true);

        Account result = adminCreationNode.createAdminManagedAccount(A_NAME, A_BIRTHDATE, A_GENDER,
                AN_IDUL, A_EMAIL, role, availablePermissions);

        Assertions.assertEquals(A_NAME, result.getName());
        Assertions.assertEquals(A_BIRTHDATE, result.getBirthDate());
        Assertions.assertEquals(A_GENDER, result.getGender());
        Assertions.assertEquals(AN_IDUL, result.getIdul());
        Assertions.assertEquals(A_EMAIL, result.getEmail());
        Assertions.assertEquals(role, result.getRole());
        Assertions.assertNotNull(result.getPermissions());
    }

    @Test
    void givenNoAdminRole_whenCreateAdminManagedAccount_thenNextNodeIsCalled() {
        role = NOT_ADMIN_ROLE;

        adminCreationNode.createAdminManagedAccount(A_NAME, A_BIRTHDATE, A_GENDER, AN_IDUL, A_EMAIL,
                role, availablePermissions);

        Mockito.verify(nextNode).createAdminManagedAccount(A_NAME, A_BIRTHDATE, A_GENDER, AN_IDUL,
                A_EMAIL, role, availablePermissions);
    }

    @Test
    void givenNoPermissions_whenCreateAdminManagedAccount_thenThrowsException() {
        Mockito.when(availablePermissions.contains(Mockito.any(Permission.class)))
                .thenReturn(false);

        Executable executable = () -> adminCreationNode.createAdminManagedAccount(A_NAME,
                A_BIRTHDATE, A_GENDER, AN_IDUL, A_EMAIL, role, availablePermissions);

        Assertions.assertThrows(AuthorizationException.class, executable);
    }

}
