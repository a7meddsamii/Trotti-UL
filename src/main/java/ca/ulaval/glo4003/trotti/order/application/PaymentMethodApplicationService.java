package ca.ulaval.glo4003.trotti.order.application;

import ca.ulaval.glo4003.trotti.order.application.dto.PaymentInfoDto;
import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.payment.domain.values.method.CreditCard;
import ca.ulaval.glo4003.trotti.order.domain.entities.buyer.Buyer;
import ca.ulaval.glo4003.trotti.order.domain.factories.PaymentMethodFactory;
import ca.ulaval.glo4003.trotti.order.domain.repositories.BuyerRepository;

public class PaymentMethodApplicationService {

    private final BuyerRepository buyerRepository;
    private final PaymentMethodFactory paymentMethodFactory;

    public PaymentMethodApplicationService(
            BuyerRepository buyerRepository,
            PaymentMethodFactory paymentMethodFactory) {
        this.buyerRepository = buyerRepository;
        this.paymentMethodFactory = paymentMethodFactory;
    }

    public void updatePaymentMethod(Idul idul, PaymentInfoDto paymentInfoDto) {
        Buyer buyer = buyerRepository.findByIdul(idul);

        CreditCard creditCard = paymentMethodFactory.createCreditCard(paymentInfoDto.cardNumber(),
                paymentInfoDto.cardHolderName(), paymentInfoDto.expirationDate(),
                paymentInfoDto.cvv());

        buyer.updatePaymentMethod(creditCard);
        buyerRepository.update(buyer);
    }

    public void deletePaymentMethod(Idul idul) {
        Buyer buyer = buyerRepository.findByIdul(idul);

        buyer.deletePaymentMethod();
        buyerRepository.update(buyer);
    }
}
