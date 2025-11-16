package ca.ulaval.glo4003.trotti.account.domain.factories.adminManagedAccountCreationChain;

import ca.ulaval.glo4003.trotti.account.domain.entities.Account;
import ca.ulaval.glo4003.trotti.account.domain.exceptions.AuthorizationException;
import ca.ulaval.glo4003.trotti.account.domain.values.*;
import ca.ulaval.glo4003.trotti.account.domain.values.permissions.Permission;
import ca.ulaval.glo4003.trotti.account.fixtures.AccountFixture;
import java.time.LocalDate;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

class TechnicianCreationNodeTest {

    private static final String A_NAME = AccountFixture.A_NAME;
    private static final LocalDate A_BIRTHDATE = AccountFixture.A_BIRTHDATE;
    private static final Gender A_GENDER = AccountFixture.A_GENDER;
    private static final Idul AN_IDUL = AccountFixture.AN_IDUL;
    private static final Email A_EMAIL = AccountFixture.AN_EMAIL;
    private static final Password A_PASSWORD = AccountFixture.A_PASSWORD;

    private Set<Permission> availablePermissions;
    private AdminManagedAccountCreationNode nextNode;
    private Role role;
    private TechnicianCreationNode technicianCreationNode;

    @BeforeEach
    void setUp() {
        availablePermissions = Mockito.mock(Set.class);
        nextNode = Mockito.mock(AdminManagedAccountCreationNode.class);
        role = Role.TECHNICIAN;
        technicianCreationNode = new TechnicianCreationNode();
        technicianCreationNode.setNext(nextNode);
    }

    @Test
    void givenTechnicianRoleAndCorrectPermissions_whenCreateAdminManagedAccount_thenAdminAccountIsCreated() {
        Mockito.when(availablePermissions.contains(Mockito.any(Permission.class))).thenReturn(true);

        Account expected = technicianCreationNode.createAdminManagedAccount(A_NAME, A_BIRTHDATE,
                A_GENDER, AN_IDUL, A_EMAIL, A_PASSWORD, role, availablePermissions);

        Assertions.assertEquals(A_NAME, expected.getName());
        Assertions.assertEquals(A_BIRTHDATE, expected.getBirthDate());
        Assertions.assertEquals(A_GENDER, expected.getGender());
        Assertions.assertEquals(AN_IDUL, expected.getIdul());
        Assertions.assertEquals(A_EMAIL, expected.getEmail());
        Assertions.assertEquals(A_PASSWORD, expected.getPassword());
        Assertions.assertEquals(role, expected.getRole());
        Assertions.assertNotNull(expected.getPermissions());
    }

    @Test
    void givenNoTechnicianRole_whenCreateAdminManagedAccount_thenNextNodeIsCalled() {
        role = Role.EMPLOYEE;

        technicianCreationNode.createAdminManagedAccount(A_NAME, A_BIRTHDATE, A_GENDER, AN_IDUL,
                A_EMAIL, A_PASSWORD, role, availablePermissions);

        Mockito.verify(nextNode).createAdminManagedAccount(A_NAME, A_BIRTHDATE, A_GENDER, AN_IDUL,
                A_EMAIL, A_PASSWORD, role, availablePermissions);
    }

    @Test
    void givenNoPermissions_whenCreateAdminManagedAccount_thenThrowsAuthorizationException() {
        Mockito.when(availablePermissions.contains(Mockito.any(Permission.class)))
                .thenReturn(false);

        Executable executable = () -> technicianCreationNode.createAdminManagedAccount(A_NAME,
                A_BIRTHDATE, A_GENDER, AN_IDUL, A_EMAIL, A_PASSWORD, role, availablePermissions);

        Assertions.assertThrows(AuthorizationException.class, executable);
    }

}
