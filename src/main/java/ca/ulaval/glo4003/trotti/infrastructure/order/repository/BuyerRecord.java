package ca.ulaval.glo4003.trotti.infrastructure.order.repository;

import ca.ulaval.glo4003.trotti.domain.account.values.Email;
import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.order.Cart;
import ca.ulaval.glo4003.trotti.domain.payment.PaymentMethod;
import java.util.Optional;

public record BuyerRecord(Idul idul, String name, Email email, Cart cart, Optional<PaymentMethod> paymentMethod) {
}
