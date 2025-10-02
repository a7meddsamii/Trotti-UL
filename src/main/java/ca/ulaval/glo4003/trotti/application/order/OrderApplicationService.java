package ca.ulaval.glo4003.trotti.application.order;

import ca.ulaval.glo4003.trotti.application.order.dto.TransactionDto;
import ca.ulaval.glo4003.trotti.application.order.mappers.TransactionMapper;
import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.communication.NotificationService;
import ca.ulaval.glo4003.trotti.domain.order.Buyer;
import ca.ulaval.glo4003.trotti.domain.order.Invoice;
import ca.ulaval.glo4003.trotti.domain.order.Order;
import ca.ulaval.glo4003.trotti.domain.order.OrderFactory;
import ca.ulaval.glo4003.trotti.domain.order.Pass;
import ca.ulaval.glo4003.trotti.domain.order.repository.BuyerRepository;
import ca.ulaval.glo4003.trotti.domain.order.repository.PassRepository;
import ca.ulaval.glo4003.trotti.domain.payment.CreditCard;
import ca.ulaval.glo4003.trotti.domain.payment.exceptions.InvalidPaymentMethodException;
import ca.ulaval.glo4003.trotti.domain.payment.services.PaymentService;
import ca.ulaval.glo4003.trotti.domain.payment.values.Transaction;
import java.util.List;

public class OrderApplicationService {

    private final BuyerRepository buyerRepository;
    private final PassRepository passRepository;
    private final OrderFactory orderFactory;
    private final PaymentService paymentService;
    private final TransactionMapper transactionMapper;
    private final NotificationService<Transaction> transactionNotificationService;
    private final NotificationService<Invoice> invoiceNotificationService;

    public OrderApplicationService(
            BuyerRepository buyerRepository,
            PassRepository passRepository,
            OrderFactory orderFactory,
            PaymentService paymentService,
            TransactionMapper transactionMapper,
            NotificationService<Transaction> transactionNotificationService,
            NotificationService<Invoice> invoiceNotificationService) {
        this.buyerRepository = buyerRepository;
        this.passRepository = passRepository;
        this.orderFactory = orderFactory;
        this.paymentService = paymentService;
        this.transactionMapper = transactionMapper;
        this.transactionNotificationService = transactionNotificationService;
        this.invoiceNotificationService = invoiceNotificationService;
    }

    public TransactionDto placeOrderFor(Idul idul, String cvv) {
        Buyer buyer = buyerRepository.findByIdul(idul);
        CreditCard paymentMethod =
                buyer.getPaymentMethod().orElseThrow(() -> new InvalidPaymentMethodException(
                        "No payment method associated with this account"));

        Transaction transaction =
                paymentService.process(paymentMethod, buyer.getCartBalance(), cvv);

        if (transaction.isSuccessful()) {
            finalizeOrderFor(buyer);
        }

        transactionNotificationService.notify(buyer.getEmail(), transaction);

        return transactionMapper.toDto(transaction);
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
