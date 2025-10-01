package ca.ulaval.glo4003.trotti.application.order;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.order.Buyer;
import ca.ulaval.glo4003.trotti.domain.order.repository.BuyerRepository;
import ca.ulaval.glo4003.trotti.domain.payment.PaymentMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class PaymentMethodApplicationServiceTest {

    private BuyerRepository buyerRepository;
    private Buyer buyer;
    private Idul idul;
    private PaymentMethod paymentMethod;

    private PaymentMethodApplicationService paymentMethodApplicationService;

    @BeforeEach
    void setUp() {
        buyerRepository = Mockito.mock(BuyerRepository.class);
        buyer = Mockito.mock(Buyer.class);
        idul = Mockito.mock(Idul.class);
        paymentMethod = Mockito.mock(PaymentMethod.class);
        paymentMethodApplicationService = new PaymentMethodApplicationService(buyerRepository);
    }

    @Test
    void givenBuyer_whenUpdatePaymentMethod_thenPaymentMethodIsUpdated() {
        Mockito.when(buyerRepository.findByIdul(idul)).thenReturn(buyer);

        paymentMethodApplicationService.updatePaymentMethod(idul, paymentMethod);

        Mockito.verify(buyer).updatePaymentMethod(paymentMethod);
    }

    @Test
    void givenBuyer_whenUpdatePaymentMethod_thenBuyerIsSaved() {
        Mockito.when(buyerRepository.findByIdul(idul)).thenReturn(buyer);

        paymentMethodApplicationService.updatePaymentMethod(idul, paymentMethod);

        Mockito.verify(buyerRepository).save(buyer);
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

        Mockito.verify(buyerRepository).save(buyer);
    }
}
