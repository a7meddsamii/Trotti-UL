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

    private static final String NAME = AccountFixture.NAME;
    private static final LocalDate BIRTHDATE = AccountFixture.BIRTHDATE;
    private static final Gender GENDER = AccountFixture.GENDER;
    private static final Idul IDUL = AccountFixture.IDUL;
    private static final Email EMAIL = AccountFixture.EMAIL;

    private StandardAccountCreationNode nextNode;
    private Role role;
    private EmployeeCreationNode employeeCreationNode;
    private EmployeeRegistryProvider employeeRegistryProvider;

    @BeforeEach
    void setUp() {
        nextNode = Mockito.mock(StandardAccountCreationNode.class);
        employeeRegistryProvider = Mockito.mock(EmployeeRegistryProvider.class);
        employeeCreationNode = new EmployeeCreationNode(employeeRegistryProvider);
        employeeCreationNode.setNext(nextNode);
    }

    @Test
    void givenEmployeeRoleAndEmployeeInRegistry_whenCreateStandardAccount_thenEmployeeAccountIsCreated() {
        role = Role.EMPLOYEE;
        Mockito.when(employeeRegistryProvider.exists(Mockito.any(Idul.class))).thenReturn(true);

        Account expected = employeeCreationNode.createStandardAccount(NAME, BIRTHDATE, GENDER,
                IDUL, EMAIL, role);

        Assertions.assertEquals(NAME, expected.getName());
        Assertions.assertEquals(BIRTHDATE, expected.getBirthDate());
        Assertions.assertEquals(GENDER, expected.getGender());
        Assertions.assertEquals(IDUL, expected.getIdul());
        Assertions.assertEquals(EMAIL, expected.getEmail());
        Assertions.assertEquals(role, expected.getRole());
        Assertions.assertNotNull(expected.getPermissions());
    }

    @Test
    void givenNoEmployeeRole_whenCreateCompanyAccount_thenNextNodeIsCalled() {
        role = Role.TECHNICIAN;

        employeeCreationNode.createStandardAccount(NAME, BIRTHDATE, GENDER, IDUL, EMAIL,
                role);

        Mockito.verify(nextNode).createStandardAccount(NAME, BIRTHDATE, GENDER, IDUL,
                EMAIL, role);
    }

    @Test
    void givenEmployeeNotInRegister_whenCreateStandardAccount_thenThrowsUnableToCreateAccountException() {
        role = Role.EMPLOYEE;
        Mockito.when(employeeRegistryProvider.exists(Mockito.any(Idul.class))).thenReturn(false);

        Executable CreatingAccountWithEmployeeNotInRegistry = () -> employeeCreationNode
                .createStandardAccount(NAME, BIRTHDATE, GENDER, IDUL, EMAIL, role);

        Assertions.assertThrows(AuthorizationException.class,
                CreatingAccountWithEmployeeNotInRegistry);
    }

}
