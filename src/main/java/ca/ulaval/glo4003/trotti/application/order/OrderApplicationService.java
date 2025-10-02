package ca.ulaval.glo4003.trotti.application.order;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.communication.EmailMessage;
import ca.ulaval.glo4003.trotti.domain.communication.EmailService;
import ca.ulaval.glo4003.trotti.domain.order.Buyer;
import ca.ulaval.glo4003.trotti.domain.order.Invoice;
import ca.ulaval.glo4003.trotti.domain.order.Order;
import ca.ulaval.glo4003.trotti.domain.order.OrderFactory;
import ca.ulaval.glo4003.trotti.domain.order.repository.BuyerRepository;
import ca.ulaval.glo4003.trotti.domain.payment.CreditCard;
import ca.ulaval.glo4003.trotti.domain.payment.services.InvoiceFormatService;
import ca.ulaval.glo4003.trotti.domain.payment.services.PaymentService;
import ca.ulaval.glo4003.trotti.domain.payment.values.Transaction;

public class OrderApplicationService {

    private final BuyerRepository buyerRepository;
    private final OrderFactory orderFactory;
    private final PaymentService paymentService;
    private final EmailService emailService;
    private final InvoiceFormatService<String> invoiceFormatService;

    public OrderApplicationService(
            BuyerRepository buyerRepository,
            OrderFactory orderFactory,
            PaymentService paymentService,
            EmailService emailService,
            InvoiceFormatService<String> invoiceFormatService) {
        this.buyerRepository = buyerRepository;
        this.orderFactory = orderFactory;
        this.paymentService = paymentService;
        this.emailService = emailService;
        this.invoiceFormatService = invoiceFormatService;
    }

    public Transaction placeOrderFor(Idul idul) {
        Buyer buyer = buyerRepository.findByIdul(idul);
        CreditCard paymentMethod = buyer.getPaymentMethod().get();

        Transaction transaction = paymentService.process(paymentMethod, buyer.getCartBalance());
        emailService.send(EmailMessage.builder().withRecipient(buyer.getEmail())
                .withSubject("Transaction Details").withBody(transaction.toString()).build());

        if (transaction.isFailed()) {
            return transaction;
        }
        Order order = orderFactory.create(idul, buyer.getCartPasses());
        Invoice invoice = order.generateInvoice();
        String formattedInvoice = invoiceFormatService.format(invoice);

        emailService.send(EmailMessage.builder().withRecipient(buyer.getEmail())
                .withSubject("Your Invoice").withBody(formattedInvoice).build());

        buyer.clearCart();
        buyerRepository.update(buyer);

        return transaction;
    }

}
