package ca.ulaval.glo4003.trotti.order.infrastructure.repositories.records;

import ca.ulaval.glo4003.trotti.account.domain.values.Email;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import java.util.List;

public record BuyerRecord(Idul idul, String name, Email email, List<PassRecord> cart, CreditCardRecord paymentMethod) {
}
