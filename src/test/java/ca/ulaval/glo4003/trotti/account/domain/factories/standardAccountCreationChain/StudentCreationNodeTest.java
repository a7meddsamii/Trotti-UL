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
    void setup() {
        nextNode = Mockito.mock(StandardAccountCreationNode.class);
        studentCreationNode = new StudentCreationNode();
        studentCreationNode.setNext(nextNode);
    }

    @Test
    void givenStudentRole_whenCreateStandardAccount_thenStudentAccountIsCreated() {
        role = Role.STUDENT;

        Account result = studentCreationNode.createStandardAccount(NAME, BIRTHDATE, GENDER, IDUL,
                EMAIL, role);

        Assertions.assertEquals(NAME, result.getName());
        Assertions.assertEquals(BIRTHDATE, result.getBirthDate());
        Assertions.assertEquals(GENDER, result.getGender());
        Assertions.assertEquals(IDUL, result.getIdul());
        Assertions.assertEquals(EMAIL, result.getEmail());
        Assertions.assertEquals(role, result.getRole());
        Assertions.assertNotNull(result.getPermissions());
    }

    @Test
    void givenNoStudentRole_whenCreateStandardAccount_thenNextNodeIsCalled() {
        role = Role.TECHNICIAN;

        studentCreationNode.createStandardAccount(NAME, BIRTHDATE, GENDER, IDUL, EMAIL, role);

        Mockito.verify(nextNode).createStandardAccount(NAME, BIRTHDATE, GENDER, IDUL, EMAIL, role);
    }

}
