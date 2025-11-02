package ca.ulaval.glo4003.trotti.order.infrastructure.repositories.records;

import ca.ulaval.glo4003.trotti.payment.domain.values.method.SecuredString;
import java.time.YearMonth;

public record CreditCardRecord(String holderName, SecuredString number, YearMonth expirationDate) {
}
