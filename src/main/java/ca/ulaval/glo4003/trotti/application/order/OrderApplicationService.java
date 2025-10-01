package ca.ulaval.glo4003.trotti.application.order;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.communication.EmailMessage;
import ca.ulaval.glo4003.trotti.domain.communication.EmailService;
import ca.ulaval.glo4003.trotti.domain.order.*;
import ca.ulaval.glo4003.trotti.domain.order.repository.BuyerRepository;
import ca.ulaval.glo4003.trotti.domain.payment.PaymentMethod;
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
        Cart cart = buyer.getCart();
        PaymentMethod paymentMethod = buyer.getPaymentMethod().get();

        Transaction transaction = paymentService.process(paymentMethod, cart.calculateAmount());

        if (transaction.isFailed()) {
            emailService.send(EmailMessage.builder()
                    .withRecipient(buyer.getEmail())
                    .withSubject("Transaction Failed")
                    .withBody(transaction.toString())
                    .build());
            return transaction;
        }

        Order order = orderFactory.create(idul, cart.getPasses());
        Invoice invoice = order.generateInvoice();
        String formattedInvoice = invoiceFormatService.format(invoice);

        emailService.send(EmailMessage.builder()
                .withRecipient(buyer.getEmail())
                .withSubject("Your Invoice")
                .withBody(formattedInvoice)
                .build());

        return transaction;
    }

}
