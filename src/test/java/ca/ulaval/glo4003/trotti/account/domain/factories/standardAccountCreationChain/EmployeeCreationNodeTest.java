package ca.ulaval.glo4003.trotti.account.domain.factories.standardAccountCreationChain;

import ca.ulaval.glo4003.trotti.account.domain.entities.Account;
import ca.ulaval.glo4003.trotti.account.domain.exceptions.AuthorizationException;
import ca.ulaval.glo4003.trotti.account.domain.provider.EmployeeRegistryProvider;
import ca.ulaval.glo4003.trotti.account.domain.values.*;
import ca.ulaval.glo4003.trotti.account.fixtures.AccountFixture;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

class EmployeeCreationNodeTest {

    private static final String A_NAME = AccountFixture.A_NAME;
    private static final LocalDate A_BIRTHDATE = AccountFixture.A_BIRTHDATE;
    private static final Gender A_GENDER = AccountFixture.A_GENDER;
    private static final Idul AN_IDUL = AccountFixture.AN_IDUL;
    private static final Email A_EMAIL = AccountFixture.AN_EMAIL;

    private StandardAccountCreationNode nextNode;
    private Role role;
    private EmployeeCreationNode employeeCreationNode;
    private EmployeeRegistryProvider employeeRegistryProvider;

    @BeforeEach
    void setup() {
        nextNode = Mockito.mock(StandardAccountCreationNode.class);
        employeeRegistryProvider = Mockito.mock(EmployeeRegistryProvider.class);
        employeeCreationNode = new EmployeeCreationNode(employeeRegistryProvider);
        employeeCreationNode.setNext(nextNode);
    }

    @Test
    void givenEmployeeRoleAndEmployeeInRegistry_whenCreateStandardAccount_thenEmployeeAccountIsCreated() {
        role = Role.EMPLOYEE;
        Mockito.when(employeeRegistryProvider.exists(Mockito.any(Idul.class))).thenReturn(true);

        Account result = employeeCreationNode.createStandardAccount(A_NAME, A_BIRTHDATE, A_GENDER,
                AN_IDUL, A_EMAIL, role);

        Assertions.assertEquals(A_NAME, result.getName());
        Assertions.assertEquals(A_BIRTHDATE, result.getBirthDate());
        Assertions.assertEquals(A_GENDER, result.getGender());
        Assertions.assertEquals(AN_IDUL, result.getIdul());
        Assertions.assertEquals(A_EMAIL, result.getEmail());
        Assertions.assertEquals(role, result.getRole());
        Assertions.assertNotNull(result.getPermissions());
    }

    @Test
    void givenNoEmployeeRole_whenCreateStandardAccount_thenNextNodeIsCalled() {
        role = Role.TECHNICIAN;

        employeeCreationNode.createStandardAccount(A_NAME, A_BIRTHDATE, A_GENDER, AN_IDUL, A_EMAIL,
                role);

        Mockito.verify(nextNode).createStandardAccount(A_NAME, A_BIRTHDATE, A_GENDER, AN_IDUL,
                A_EMAIL, role);
    }

    @Test
    void givenEmployeeNotInRegister_whenCreateStandardAccount_thenThrowsAuthorizationException() {
        role = Role.EMPLOYEE;
        Mockito.when(employeeRegistryProvider.exists(Mockito.any(Idul.class))).thenReturn(false);

        Executable creatingAccountWithEmployeeNotInRegistry = () -> employeeCreationNode
                .createStandardAccount(A_NAME, A_BIRTHDATE, A_GENDER, AN_IDUL, A_EMAIL, role);

        Assertions.assertThrows(AuthorizationException.class,
                creatingAccountWithEmployeeNotInRegistry);
    }

}
