package ca.ulaval.glo4003.trotti.application.order;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.order.*;
import ca.ulaval.glo4003.trotti.domain.order.repository.BuyerRepository;
import ca.ulaval.glo4003.trotti.domain.order.repository.PassRepository;
import ca.ulaval.glo4003.trotti.domain.payment.CreditCard;
import ca.ulaval.glo4003.trotti.domain.payment.exceptions.InvalidPaymentMethodException;
import ca.ulaval.glo4003.trotti.domain.payment.services.PaymentService;
import ca.ulaval.glo4003.trotti.domain.payment.values.Transaction;
import ca.ulaval.glo4003.trotti.domain.communication.NotificationService;
import java.util.List;

public class OrderApplicationService {

    private final BuyerRepository buyerRepository;
    private final PassRepository passRepository;
    private final OrderFactory orderFactory;
    private final PaymentService paymentService;
    private final NotificationService<Transaction> transactionNotificationService;
    private final NotificationService<Invoice> invoiceNotificationService;

    public OrderApplicationService(
            BuyerRepository buyerRepository,
            PassRepository passRepository,
            OrderFactory orderFactory,
            PaymentService paymentService,
            NotificationService<Transaction> transactionNotificationService,
            NotificationService<Invoice> invoiceNotificationService) {
        this.buyerRepository = buyerRepository;
        this.passRepository = passRepository;
        this.orderFactory = orderFactory;
        this.paymentService = paymentService;
        this.transactionNotificationService = transactionNotificationService;
        this.invoiceNotificationService = invoiceNotificationService;
    }

    public Transaction placeOrderFor(Idul idul, String cvv) {
        Buyer buyer = buyerRepository.findByIdul(idul);
        CreditCard paymentMethod =
                buyer.getPaymentMethod().orElseThrow(() -> new InvalidPaymentMethodException(
                        "No payment method associated with this account"));

        Transaction transaction =
                paymentService.process(paymentMethod, buyer.getCartBalance(), cvv);

        if (transaction.isSuccess()) {
            finalizeOrderFor(buyer);
        }

        transactionNotificationService.notify(buyer.getEmail(), transaction);

        return transaction;
    }

    private void finalizeOrderFor(Buyer buyer) {
        List<Pass> boughtPasses = buyer.confirmCartPasses();
        buyerRepository.update(buyer);
        passRepository.saveAll(boughtPasses);
        Order order = orderFactory.create(buyer.getIdul(), boughtPasses);
        Invoice invoice = order.generateInvoice();
        invoiceNotificationService.notify(buyer.getEmail(), invoice);
    }

}
