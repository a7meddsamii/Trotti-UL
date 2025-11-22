package ca.ulaval.glo4003.trotti.account.domain.entities;

import ca.ulaval.glo4003.trotti.account.domain.values.Password;
import ca.ulaval.glo4003.trotti.account.fixtures.AccountFixture;
import java.time.LocalDate;
import java.time.Month;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class AccountTest {

    private static final LocalDate A_GIVEN_BIRTHDATE = LocalDate.of(2001, Month.FEBRUARY, 10);
    private static final int EXPECTED_AGE = 24;

    private Account account;

    @BeforeEach
    void setup() {
        account = createAccountPassword();
    }

    @Test
    void givenBirthDate_whenGetAge_thenReturnCorrectAge() {
        account = new AccountFixture().withBirthDate(A_GIVEN_BIRTHDATE).build();

        Assertions.assertEquals(EXPECTED_AGE, account.getAge());
    }



    private Account createAccountPassword() {
        return new Account(AccountFixture.A_NAME, AccountFixture.A_BIRTHDATE,
                AccountFixture.A_GENDER, AccountFixture.AN_IDUL, AccountFixture.AN_EMAIL,
                AccountFixture.A_ROLE, AccountFixture.A_SET_OF_PERMISSION);
    }
}
