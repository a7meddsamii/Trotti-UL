package ca.ulaval.glo4003.trotti.infrastructure.order.mappers;

import ca.ulaval.glo4003.trotti.domain.order.Buyer;
import ca.ulaval.glo4003.trotti.domain.order.Cart;
import ca.ulaval.glo4003.trotti.domain.order.Pass;
import ca.ulaval.glo4003.trotti.domain.payment.CreditCard;
import ca.ulaval.glo4003.trotti.infrastructure.order.repository.records.BuyerRecord;
import ca.ulaval.glo4003.trotti.infrastructure.order.repository.records.CreditCardRecord;
import ca.ulaval.glo4003.trotti.infrastructure.order.repository.records.PassRecord;
import java.util.ArrayList;
import java.util.List;

public class BuyerPersistenceMapper {

    public BuyerRecord toRecord(Buyer buyer) {
        CreditCardRecord creditCard = buyer.getPaymentMethod().isPresent()
                ? toPaymentMethodRecord(buyer.getPaymentMethod().get())
                : null;

        return new BuyerRecord(buyer.getBuyerIdul(), buyer.getName(), buyer.getEmail(),
                toCartRecord(buyer.getCart()), creditCard);
    }

    public Buyer toDomain(BuyerRecord buyerFound) {
        return new Buyer(buyerFound.idul(), buyerFound.name(), buyerFound.email(),
                toCartDomain(buyerFound.cart()), toPaymentMethodDomain(buyerFound.paymentMethod()));
    }

    private List<PassRecord> toCartRecord(Cart cart) {
        List<PassRecord> passesRecord = new ArrayList<>();
        cart.getPasses()
                .forEach(pass -> passesRecord.add(
                        new PassRecord(pass.getId(), pass.getBuyerIdul(), pass.getMaximumTravelingTime(),
                                pass.getSession(), pass.getBillingFrequency())));

        return passesRecord;
    }

    private CreditCardRecord toPaymentMethodRecord(CreditCard creditCard) {
        return new CreditCardRecord(creditCard.getCardHolderName(), creditCard.getSecuredString(),
                creditCard.getExpiryDate());
    }

    private Cart toCartDomain(List<PassRecord> passRecords) {
        List<Pass> passes = passRecords.stream().map(this::toPassDomain).toList();
        return new Cart(passes);
    }

    private Pass toPassDomain(PassRecord record) {
        return new Pass(record.maximumDailyTravelTime(), record.session(),
                record.billingFrequency(), record.passId(), record.owner());
    }

    private CreditCard toPaymentMethodDomain(CreditCardRecord record) {
        if (record == null)
            return null;
        return CreditCard.from(record.number(), record.holderName(), record.expirationDate());
    }
}
