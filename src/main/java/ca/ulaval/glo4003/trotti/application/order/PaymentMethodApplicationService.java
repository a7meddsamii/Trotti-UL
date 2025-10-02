package ca.ulaval.glo4003.trotti.application.order;

import ca.ulaval.glo4003.trotti.application.order.dto.PaymentInfoDto;
import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.order.Buyer;
import ca.ulaval.glo4003.trotti.domain.order.PaymentMethodFactory;
import ca.ulaval.glo4003.trotti.domain.order.repository.BuyerRepository;
import ca.ulaval.glo4003.trotti.domain.payment.CreditCard;

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
