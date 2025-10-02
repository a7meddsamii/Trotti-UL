package ca.ulaval.glo4003.trotti.application.order;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.order.Buyer;
import ca.ulaval.glo4003.trotti.domain.order.repository.BuyerRepository;
import ca.ulaval.glo4003.trotti.domain.payment.CreditCard;
import ca.ulaval.glo4003.trotti.domain.payment.PaymentMethod;

public class PaymentMethodApplicationService {

    private final BuyerRepository buyerRepository;

    public PaymentMethodApplicationService(BuyerRepository buyerRepository) {
        this.buyerRepository = buyerRepository;
    }

    public void updatePaymentMethod(Idul idul, CreditCard paymentMethod) {
        Buyer buyer = buyerRepository.findByIdul(idul);
        buyer.updatePaymentMethod(paymentMethod);
        buyerRepository.update(buyer);
    }

    public void deletePaymentMethod(Idul idul) {
        Buyer buyer = buyerRepository.findByIdul(idul);
        buyer.deletePaymentMethod();
        buyerRepository.update(buyer);
    }
}
