package ca.ulaval.glo4003.trotti.fixtures;

import ca.ulaval.glo4003.trotti.domain.payment.CreditCard;
import ca.ulaval.glo4003.trotti.domain.payment.services.DataEncoder;
import ca.ulaval.glo4003.trotti.domain.payment.utilities.SecuredString;
import java.time.YearMonth;
import org.mockito.Mockito;

public class CreditCardFixture {

    public static final String A_CARD_NUMBER = "1111";
    public static final String A_CARD_HOLDER_NAME = "John Doe";
    public static final YearMonth AN_EXPIRY_DATE = YearMonth.of(2026, 12);

    private final DataEncoder encoder = Mockito.mock(DataEncoder.class);

    public CreditCard build() {
        return CreditCard.from(SecuredString.fromPlain(A_CARD_NUMBER, encoder), A_CARD_HOLDER_NAME, AN_EXPIRY_DATE);
    }
}
