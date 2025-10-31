package ca.ulaval.glo4003.trotti.payment.fixture;

import ca.ulaval.glo4003.trotti.payment.domain.security.DataCodec;
import ca.ulaval.glo4003.trotti.payment.domain.values.method.CreditCard;
import ca.ulaval.glo4003.trotti.payment.domain.values.method.SecuredString;
import java.time.YearMonth;
import org.mockito.Mockito;

public class CreditCardFixture {

    public static final String A_CARD_NUMBER = "1111";
    public static final String A_CARD_HOLDER_NAME = "John Doe";
    public static final YearMonth AN_EXPIRY_DATE = YearMonth.of(2026, 12);

    private final DataCodec encoder = Mockito.mock(DataCodec.class);

    public CreditCard build() {
        return CreditCard.from(SecuredString.fromPlain(A_CARD_NUMBER, encoder), A_CARD_HOLDER_NAME,
                AN_EXPIRY_DATE);
    }
}
