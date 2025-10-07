package ca.ulaval.glo4003.trotti.infrastructure.order.repository.records;

import ca.ulaval.glo4003.trotti.domain.payment.values.method.SecuredString;
import java.time.YearMonth;

public record CreditCardRecord(String holderName, SecuredString number, YearMonth expirationDate) {
}
