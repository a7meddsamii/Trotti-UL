package ca.ulaval.glo4003.trotti.application.order;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.communication.EmailService;
import ca.ulaval.glo4003.trotti.domain.order.Buyer;
import ca.ulaval.glo4003.trotti.domain.order.Cart;
import ca.ulaval.glo4003.trotti.domain.order.Order;
import ca.ulaval.glo4003.trotti.domain.order.OrderFactory;
import ca.ulaval.glo4003.trotti.domain.order.repository.BuyerRepository;
import ca.ulaval.glo4003.trotti.domain.payment.CreditCard;
import ca.ulaval.glo4003.trotti.domain.payment.services.PaymentService;
import java.lang.module.InvalidModuleDescriptorException;

public class OrderApplicationService {

    private final BuyerRepository buyerRepository;
    private final OrderFactory orderFactory;
    private final PaymentService paymentService;
    private final EmailService emailService;

    public OrderApplicationService(
            BuyerRepository buyerRepository,
            OrderFactory orderFactory,
            PaymentService paymentService,
            EmailService emailService) {
        this.buyerRepository = buyerRepository;
        this.orderFactory = orderFactory;
        this.paymentService = paymentService;
        this.emailService = emailService;
    }

    public Id placeOrderFor(Idul idul) {
        Buyer buyer = buyerRepository.findByIdul(idul);
        Cart cart = buyer.getCart();

        CreditCard paymentMethod = buyer.getPaymentMethod().orElseThrow(
                () -> new InvalidModuleDescriptorException("No payment method found."));
        paymentService.process(paymentMethod, cart.calculateAmount());

        Order order = orderFactory.create(idul, cart.getPasses());
        order.generateInvoice();
        // Create invoice formatted as a string
        // emailService send invoice

        return Id.randomId();
    }
}
