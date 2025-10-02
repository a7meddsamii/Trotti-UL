package ca.ulaval.glo4003.trotti.infrastructure.order.repository.record;

import ca.ulaval.glo4003.trotti.domain.account.values.Email;
import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import java.util.List;

public record BuyerRecord(Idul idul, String name, Email email, List<PassRecord> cart, CreditCardRecord paymentMethod) {
}
