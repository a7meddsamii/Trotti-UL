package ca.ulaval.glo4003.trotti.infrastructure.order.repository.record;

import java.time.YearMonth;

public record CreditCardRecord(String holderName, String number, YearMonth expirationDate, String cvv) {
}
