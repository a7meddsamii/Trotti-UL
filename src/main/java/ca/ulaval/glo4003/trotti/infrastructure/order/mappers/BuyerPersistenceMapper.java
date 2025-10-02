package ca.ulaval.glo4003.trotti.infrastructure.order.mappers;

import ca.ulaval.glo4003.trotti.domain.order.Buyer;
import ca.ulaval.glo4003.trotti.domain.order.Cart;
import ca.ulaval.glo4003.trotti.domain.order.Pass;
import ca.ulaval.glo4003.trotti.domain.payment.CreditCard;
import ca.ulaval.glo4003.trotti.domain.payment.PaymentMethod;
import ca.ulaval.glo4003.trotti.infrastructure.order.repository.record.BuyerRecord;
import ca.ulaval.glo4003.trotti.infrastructure.order.repository.record.CreditCardRecord;
import ca.ulaval.glo4003.trotti.infrastructure.order.repository.record.PassRecord;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BuyerPersistenceMapper {

    public BuyerRecord toDTO(Buyer buyer) {
		CreditCardRecord creditCard =  buyer.getPaymentMethod().isPresent() ? toDtoPaymentMethod(buyer.getPaymentMethod().get()) : null;
		
        return new BuyerRecord(buyer.getIdul(), buyer.getName(), buyer.getEmail(), toDtoCart(buyer.getCart()), creditCard);
    }

    public Buyer toDomain(BuyerRecord buyerFound) {
		Cart cart = toDomainCart(buyerFound.cart());
        return buyerFound.paymentMethod()
                .map(paymentMethod -> new Buyer(buyerFound.idul(), buyerFound.name(),
                        buyerFound.email(), toDomainCart(buyerFound.cart()), paymentMethod))
                .orElseGet(() -> new Buyer(buyerFound.idul(), buyerFound.name(), buyerFound.email(),
                        toDomainCart(buyerFound.cart())));
    }

    private List<PassRecord> toDtoCart(Cart cart) {
        List<PassRecord> passesRecord = new ArrayList<>();
        cart.getList().forEach(pass -> passesRecord.add(new PassRecord(pass.getId(), pass.getIdul(),
                pass.getMaximumTravelingTime(), pass.getSession(), pass.getBillingFrequency())));

        return passesRecord;
    }

    private CreditCardRecord toDtoPaymentMethod(PaymentMethod paymentMethod) {
		return new CreditCardRecord()
    }

    private Cart toDomainCart(List<PassRecord> passRecords) {
        List<Pass> passes = passRecords.stream().map(this::toDomainPass).toList();
        return new Cart(passes);
    }

    private Pass toDomainPass(PassRecord record) {
        return new Pass(record.maximumDailyTravelTime(), record.session(), record.billingFrequency(), record.passId(), record.owner());
    }
}
