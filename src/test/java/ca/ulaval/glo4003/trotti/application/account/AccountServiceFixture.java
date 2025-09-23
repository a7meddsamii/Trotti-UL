package ca.ulaval.glo4003.trotti.application.account;

import ca.ulaval.glo4003.trotti.application.account.dto.CreateAccount;
import ca.ulaval.glo4003.trotti.domain.account.Email;
import ca.ulaval.glo4003.trotti.domain.account.Idul;
import ca.ulaval.glo4003.trotti.domain.account.Password;
import ca.ulaval.glo4003.trotti.domain.account.authentication.AuthenticationToken;
import org.mockito.Mockito;

import java.time.LocalDate;

public class AccountServiceFixture {
    public static final String A_NAME = "Camavinga";
    public static final LocalDate A_BIRTHDATE = LocalDate.of(2000, 1, 1);
    public static final String A_GENDER_STRING = "MALE";
    public static final String AN_IDUL_STRING = "JD12345";
    public static final String AN_EMAIL_STRING = "john.doe@ulaval.ca";
    public static final String A_RAW_PASSWORD = "StrongPass1!";
    public static final String A_TOKEN_VALUE = "jwt-token";
    public static final AuthenticationToken AN_AUTH_TOKEN = AuthenticationToken.from(A_TOKEN_VALUE);

    public static final Email AN_EMAIL = Email.from(AN_EMAIL_STRING);
    public static final Idul AN_IDUL = Idul.from(AN_IDUL_STRING);
    public static final Password A_PASSWORD = Mockito.mock(Password.class);

    public static CreateAccount aCreateAccountRequest() {
        return new CreateAccount(
                A_NAME,
                A_BIRTHDATE,
                A_GENDER_STRING,
                AN_IDUL_STRING,
                AN_EMAIL_STRING,
                A_RAW_PASSWORD
        );
    }
}
