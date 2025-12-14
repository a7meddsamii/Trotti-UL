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

    private static final String NAME = AccountFixture.NAME;
    private static final LocalDate BIRTHDATE = AccountFixture.BIRTHDATE;
    private static final Gender GENDER = AccountFixture.GENDER;
    private static final Idul IDUL = AccountFixture.IDUL;
    private static final Email EMAIL = AccountFixture.EMAIL;
    private static final Role NOT_ADMIN_ROLE = Role.EMPLOYEE;

    private Set<Permission> availablePermissions;
    private AdminManagedAccountCreationNode nextNode;
    private Role role;
    private AdminCreationNode adminCreationNode;

    @BeforeEach
    void setUp() {
        availablePermissions = Mockito.mock(Set.class);
        nextNode = Mockito.mock(AdminManagedAccountCreationNode.class);
        role = Role.ADMIN;
        adminCreationNode = new AdminCreationNode();
        adminCreationNode.setNext(nextNode);
    }

    @Test
    void givenAdminRoleAndCorrectPermissions_whenCreateCompanyAccount_thenAdminAccountIsCreated() {
        Mockito.when(availablePermissions.contains(Mockito.any(Permission.class))).thenReturn(true);

        Account expected = adminCreationNode.createAdminManagedAccount(NAME, BIRTHDATE,
                GENDER, IDUL, EMAIL, role, availablePermissions);

        Assertions.assertEquals(NAME, expected.getName());
        Assertions.assertEquals(BIRTHDATE, expected.getBirthDate());
        Assertions.assertEquals(GENDER, expected.getGender());
        Assertions.assertEquals(IDUL, expected.getIdul());
        Assertions.assertEquals(EMAIL, expected.getEmail());
        Assertions.assertEquals(role, expected.getRole());
        Assertions.assertNotNull(expected.getPermissions());
    }

    @Test
    void givenNoAdminRole_whenCreateCompanyAccount_thenNextNodeIsCalled() {
        role = NOT_ADMIN_ROLE;

        adminCreationNode.createAdminManagedAccount(NAME, BIRTHDATE, GENDER, IDUL, EMAIL,
                role, availablePermissions);

        Mockito.verify(nextNode).createAdminManagedAccount(NAME, BIRTHDATE, GENDER, IDUL,
                EMAIL, role, availablePermissions);
    }

    @Test
    void givenNoPermissions_whenCreateCompanyAccount_thenThrowsAuthorizationException() {
        Mockito.when(availablePermissions.contains(Mockito.any(Permission.class)))
                .thenReturn(false);

        Executable executable = () -> adminCreationNode.createAdminManagedAccount(NAME,
                BIRTHDATE, GENDER, IDUL, EMAIL, role, availablePermissions);

        Assertions.assertThrows(AuthorizationException.class, executable);
    }

}
