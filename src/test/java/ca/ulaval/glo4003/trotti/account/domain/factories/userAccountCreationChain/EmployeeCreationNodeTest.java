package ca.ulaval.glo4003.trotti.account.domain.factories.userAccountCreationChain;

import ca.ulaval.glo4003.trotti.account.domain.entities.Account;
import ca.ulaval.glo4003.trotti.account.domain.values.*;
import ca.ulaval.glo4003.trotti.account.fixtures.AccountFixture;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

class EmployeeCreationNodeTest {

    private static final String A_NAME = AccountFixture.A_NAME;
    private static final LocalDate A_BIRTHDATE = AccountFixture.A_BIRTHDATE;
    private static final Gender A_GENDER = AccountFixture.A_GENDER;
    private static final Idul AN_IDUL = AccountFixture.AN_IDUL;
    private static final Email A_EMAIL = AccountFixture.AN_EMAIL;
    private static final Password A_PASSWORD = AccountFixture.A_PASSWORD;


    private UserAccountCreationNode nextNode;
    private Role role;
    private EmployeeCreationNode employeeCreationNode;


    @BeforeEach
    void setUp() {
        nextNode = Mockito.mock(UserAccountCreationNode.class);
        employeeCreationNode = new EmployeeCreationNode(nextNode);
    }

    @Test
    void givenTechnicianRoleAndCorrectPermissions_whenCreateCompanyAccount_thenAdminAccountIsCreated() {
        role = Role.EMPLOYEE;

        Account expected = employeeCreationNode.CreateUserAccount(A_NAME,A_BIRTHDATE,A_GENDER,AN_IDUL, A_EMAIL,A_PASSWORD,role);

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
    void givenNoEmployeeRole_whenCreateCompanyAccount_thenNextNodeIsCalled() {
        role = Role.TECHNICIAN;

        employeeCreationNode.CreateUserAccount(A_NAME,A_BIRTHDATE,A_GENDER,AN_IDUL, A_EMAIL,A_PASSWORD,role);

        Mockito.verify(nextNode).CreateUserAccount(A_NAME,A_BIRTHDATE,A_GENDER,AN_IDUL, A_EMAIL,A_PASSWORD,role);
    }



}