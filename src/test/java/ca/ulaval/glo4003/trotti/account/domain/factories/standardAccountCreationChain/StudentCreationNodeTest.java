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

    private static final String A_NAME = AccountFixture.A_NAME;
    private static final LocalDate A_BIRTHDATE = AccountFixture.A_BIRTHDATE;
    private static final Gender A_GENDER = AccountFixture.A_GENDER;
    private static final Idul AN_IDUL = AccountFixture.AN_IDUL;
    private static final Email A_EMAIL = AccountFixture.AN_EMAIL;

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
        // given
        role = Role.STUDENT;

        // when
        Account result = studentCreationNode.createStandardAccount(A_NAME, A_BIRTHDATE, A_GENDER,
                AN_IDUL, A_EMAIL, role);

        // then
        Assertions.assertEquals(A_NAME, result.getName());
        Assertions.assertEquals(A_BIRTHDATE, result.getBirthDate());
        Assertions.assertEquals(A_GENDER, result.getGender());
        Assertions.assertEquals(AN_IDUL, result.getIdul());
        Assertions.assertEquals(A_EMAIL, result.getEmail());
        Assertions.assertEquals(role, result.getRole());
        Assertions.assertNotNull(result.getPermissions());
    }

    @Test
    void givenNoStudentRole_whenCreateStandardAccount_thenNextNodeIsCalled() {
        // given
        role = Role.TECHNICIAN;

        // when
        studentCreationNode.createStandardAccount(A_NAME, A_BIRTHDATE, A_GENDER, AN_IDUL, A_EMAIL,
                role);

        // then
        Mockito.verify(nextNode).createStandardAccount(A_NAME, A_BIRTHDATE, A_GENDER, AN_IDUL,
                A_EMAIL, role);
    }

}
