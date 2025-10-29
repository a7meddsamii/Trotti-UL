package ca.ulaval.glo4003.trotti.application.order;

import ca.ulaval.glo4003.trotti.order.application.PaymentMethodApplicationService;
import ca.ulaval.glo4003.trotti.order.application.dto.PaymentInfoDto;
import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.payment.domain.values.method.CreditCard;
import ca.ulaval.glo4003.trotti.order.domain.entities.buyer.Buyer;
import ca.ulaval.glo4003.trotti.order.domain.factories.PaymentMethodFactory;
import ca.ulaval.glo4003.trotti.order.domain.repositories.BuyerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class PaymentMethodApplicationServiceTest {

    private Buyer buyer;
    private Idul idul;
    private PaymentInfoDto paymentMethod;
    private CreditCard creditCard;
    private BuyerRepository buyerRepository;
    private PaymentMethodFactory paymentMethodFactory;

    private PaymentMethodApplicationService paymentMethodApplicationService;

    @BeforeEach
    void setUp() {
        buyerRepository = Mockito.mock(BuyerRepository.class);
        buyer = Mockito.mock(Buyer.class);
        idul = Mockito.mock(Idul.class);
        paymentMethod = Mockito.mock(PaymentInfoDto.class);
        paymentMethodFactory = Mockito.mock(PaymentMethodFactory.class);
        creditCard = Mockito.mock(CreditCard.class);
        paymentMethodApplicationService =
                new PaymentMethodApplicationService(buyerRepository, paymentMethodFactory);
    }

    @Test
    void givenBuyer_whenUpdatePaymentMethod_thenPaymentMethodIsUpdated() {
        Mockito.when(buyerRepository.findByIdul(idul)).thenReturn(buyer);
        Mockito.when(paymentMethodFactory.createCreditCard(paymentMethod.cardNumber(),
                paymentMethod.cardHolderName(), paymentMethod.expirationDate(),
                paymentMethod.cvv())).thenReturn(creditCard);

        paymentMethodApplicationService.updatePaymentMethod(idul, paymentMethod);

        Mockito.verify(buyer).updatePaymentMethod(creditCard);
        Mockito.verify(paymentMethodFactory).createCreditCard(paymentMethod.cardNumber(),
                paymentMethod.cardHolderName(), paymentMethod.expirationDate(),
                paymentMethod.cvv());
    }

    @Test
    void givenBuyer_whenUpdatePaymentMethod_thenBuyerIsSaved() {
        Mockito.when(buyerRepository.findByIdul(idul)).thenReturn(buyer);

        paymentMethodApplicationService.updatePaymentMethod(idul, paymentMethod);

        Mockito.verify(buyerRepository).update(buyer);
    }

    @Test
    void givenBuyer_whenDeletePaymentMethod_thenPaymentMethodIsDeleted() {
        Mockito.when(buyerRepository.findByIdul(idul)).thenReturn(buyer);

        paymentMethodApplicationService.deletePaymentMethod(idul);

        Mockito.verify(buyer).deletePaymentMethod();
    }

    @Test
    void givenBuyer_whenDeletePaymentMethod_thenBuyerIsSaved() {
        Mockito.when(buyerRepository.findByIdul(idul)).thenReturn(buyer);

        paymentMethodApplicationService.deletePaymentMethod(idul);

        Mockito.verify(buyerRepository).update(buyer);
    }
}
