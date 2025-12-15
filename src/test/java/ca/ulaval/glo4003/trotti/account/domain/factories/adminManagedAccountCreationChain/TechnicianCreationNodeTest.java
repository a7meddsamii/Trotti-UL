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

class TechnicianCreationNodeTest {

    private static final String NAME = AccountFixture.NAME;
    private static final LocalDate BIRTHDATE = AccountFixture.BIRTHDATE;
    private static final Gender GENDER = AccountFixture.GENDER;
    private static final Idul IDUL = AccountFixture.IDUL;
    private static final Email EMAIL = AccountFixture.EMAIL;

    private Set<Permission> availablePermissions;
    private AdminManagedAccountCreationNode nextNode;
    private Role role;
    private TechnicianCreationNode technicianCreationNode;

    @BeforeEach
    void setup() {
        availablePermissions = Mockito.mock(Set.class);
        nextNode = Mockito.mock(AdminManagedAccountCreationNode.class);
        role = Role.TECHNICIAN;
        technicianCreationNode = new TechnicianCreationNode();
        technicianCreationNode.setNext(nextNode);
    }

    @Test
    void givenTechnicianRoleAndCorrectPermissions_whenCreateAdminManagedAccount_thenTechnicianAccountIsCreated() {
        Mockito.when(availablePermissions.contains(Mockito.any(Permission.class))).thenReturn(true);

        Account result = technicianCreationNode.createAdminManagedAccount(NAME, BIRTHDATE, GENDER,
                IDUL, EMAIL, role, availablePermissions);

        Assertions.assertEquals(NAME, result.getName());
        Assertions.assertEquals(BIRTHDATE, result.getBirthDate());
        Assertions.assertEquals(GENDER, result.getGender());
        Assertions.assertEquals(IDUL, result.getIdul());
        Assertions.assertEquals(EMAIL, result.getEmail());
        Assertions.assertEquals(role, result.getRole());
        Assertions.assertNotNull(result.getPermissions());
    }

    @Test
    void givenNoTechnicianRole_whenCreateAdminManagedAccount_thenNextNodeIsCalled() {
        role = Role.EMPLOYEE;

        technicianCreationNode.createAdminManagedAccount(NAME, BIRTHDATE, GENDER, IDUL, EMAIL, role,
                availablePermissions);

        Mockito.verify(nextNode).createAdminManagedAccount(NAME, BIRTHDATE, GENDER, IDUL, EMAIL,
                role, availablePermissions);
    }

    @Test
    void givenNoPermissions_whenCreateAdminManagedAccount_thenThrowsException() {
        Mockito.when(availablePermissions.contains(Mockito.any(Permission.class)))
                .thenReturn(false);

        Executable executable = () -> technicianCreationNode.createAdminManagedAccount(NAME,
                BIRTHDATE, GENDER, IDUL, EMAIL, role, availablePermissions);

        Assertions.assertThrows(AuthorizationException.class, executable);
    }

}
