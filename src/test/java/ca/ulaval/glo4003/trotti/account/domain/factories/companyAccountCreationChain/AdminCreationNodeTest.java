package ca.ulaval.glo4003.trotti.account.domain.factories.companyAccountCreationChain;


import ca.ulaval.glo4003.trotti.account.domain.entities.Account;
import ca.ulaval.glo4003.trotti.account.domain.exceptions.AuthorizationException;
import ca.ulaval.glo4003.trotti.account.domain.values.*;
import ca.ulaval.glo4003.trotti.account.domain.values.permissions.Permission;
import ca.ulaval.glo4003.trotti.account.fixtures.AccountFixture;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Set;


class AdminCreationNodeTest {

    private static final String A_NAME = AccountFixture.A_NAME;
    private static final LocalDate A_BIRTHDATE = AccountFixture.A_BIRTHDATE;
    private static final Gender A_GENDER = AccountFixture.A_GENDER;
    private static final Idul AN_IDUL = AccountFixture.AN_IDUL;
    private static final Email A_EMAIL = AccountFixture.AN_EMAIL;
    private static final Password  A_PASSWORD = AccountFixture.A_PASSWORD;
    private static final  Role NOT_ADMIN_ROLE = Role.EMPLOYEE;


    private Set<Permission> availablePermissions;
    private CompanyAccountCreationNode nextNode;
    private Role role;
    private AdminCreationNode adminCreationNode;


    @BeforeEach
    void setUp() {
        availablePermissions = Mockito.mock(Set.class);
        nextNode = Mockito.mock(CompanyAccountCreationNode.class);
        role = Role.ADMIN;
        adminCreationNode = new AdminCreationNode(nextNode);
    }

    @Test
    void givenAdminRoleAndCorrectPermissions_whenCreateCompanyAccount_thenAdminAccountIsCreated() {
        Mockito.when(availablePermissions.contains(Mockito.any(Permission.class))).thenReturn(true);

        Account expected = adminCreationNode.CreateCompanyAccount(A_NAME,A_BIRTHDATE,A_GENDER,AN_IDUL, A_EMAIL,A_PASSWORD,role,availablePermissions );

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
    void givenNoAdminRole_whenCreateCompanyAccount_thenNextNodeIsCalled() {
        role = NOT_ADMIN_ROLE;

        adminCreationNode.CreateCompanyAccount(A_NAME,A_BIRTHDATE,A_GENDER,AN_IDUL, A_EMAIL,A_PASSWORD,role,availablePermissions );

        Mockito.verify(nextNode).CreateCompanyAccount(A_NAME,A_BIRTHDATE,A_GENDER,AN_IDUL, A_EMAIL,A_PASSWORD,role,availablePermissions);
    }

    @Test
    void givenNoPermissions_whenCreateCompanyAccount_thenThrowsAuthorizationException() {
        Mockito.when(availablePermissions.contains(Mockito.any(Permission.class))).thenReturn(false);

        Executable executable = () -> adminCreationNode.CreateCompanyAccount(A_NAME,A_BIRTHDATE,A_GENDER,AN_IDUL, A_EMAIL,A_PASSWORD,role,availablePermissions);

        Assertions.assertThrows(AuthorizationException.class,executable);
    }


}