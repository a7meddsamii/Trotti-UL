package ca.ulaval.glo4003.trotti.account.domain.factories.standardAccountCreationChain;

import ca.ulaval.glo4003.trotti.account.domain.entities.Account;
import ca.ulaval.glo4003.trotti.account.domain.values.*;
import ca.ulaval.glo4003.trotti.account.fixtures.AccountFixture;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

    @BeforeEach
    void setUp() {
        nextNode = Mockito.mock(StandardAccountCreationNode.class);
        employeeCreationNode = new EmployeeCreationNode();
        employeeCreationNode.setNext(nextNode);
    }

    @Test
    void givenTechnicianRoleAndCorrectPermissions_whenCreateCompanyAccount_thenAdminAccountIsCreated() {
        role = Role.EMPLOYEE;

        Account expected = employeeCreationNode.createStandardAccount(A_NAME, A_BIRTHDATE, A_GENDER,
                AN_IDUL, A_EMAIL, role);

        Assertions.assertEquals(A_NAME, expected.getName());
        Assertions.assertEquals(A_BIRTHDATE, expected.getBirthDate());
        Assertions.assertEquals(A_GENDER, expected.getGender());
        Assertions.assertEquals(AN_IDUL, expected.getIdul());
        Assertions.assertEquals(A_EMAIL, expected.getEmail());
        Assertions.assertEquals(role, expected.getRole());
        Assertions.assertNotNull(expected.getPermissions());
    }

    @Test
    void givenNoEmployeeRole_whenCreateCompanyAccount_thenNextNodeIsCalled() {
        role = Role.TECHNICIAN;

        employeeCreationNode.createStandardAccount(A_NAME, A_BIRTHDATE, A_GENDER, AN_IDUL, A_EMAIL, role);

        Mockito.verify(nextNode).createStandardAccount(A_NAME, A_BIRTHDATE, A_GENDER, AN_IDUL,
                A_EMAIL, role);
    }

}
