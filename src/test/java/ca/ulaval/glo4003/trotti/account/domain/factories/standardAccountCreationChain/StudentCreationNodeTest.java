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

class StudentCreationNodeTest {

    private static final String NAME = AccountFixture.NAME;
    private static final LocalDate BIRTHDATE = AccountFixture.BIRTHDATE;
    private static final Gender GENDER = AccountFixture.GENDER;
    private static final Idul IDUL = AccountFixture.IDUL;
    private static final Email EMAIL = AccountFixture.EMAIL;

    private StandardAccountCreationNode nextNode;
    private Role role;
    private StudentCreationNode studentCreationNode;

    @BeforeEach
    void setUp() {
        nextNode = Mockito.mock(StandardAccountCreationNode.class);
        studentCreationNode = new StudentCreationNode();
        studentCreationNode.setNext(nextNode);
    }

    @Test
    void givenStudentRole_whenCreateStandardAccount_thenStudentAccountIsCreated() {
        role = Role.STUDENT;

        Account expected = studentCreationNode.createStandardAccount(NAME, BIRTHDATE, GENDER,
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
    void givenNoStudentRole_whenCreateCompanyAccount_thenNextNodeIsCalled() {
        role = Role.TECHNICIAN;

        studentCreationNode.createStandardAccount(NAME, BIRTHDATE, GENDER, IDUL, EMAIL,
                role);

        Mockito.verify(nextNode).createStandardAccount(NAME, BIRTHDATE, GENDER, IDUL,
                EMAIL, role);
    }

}
