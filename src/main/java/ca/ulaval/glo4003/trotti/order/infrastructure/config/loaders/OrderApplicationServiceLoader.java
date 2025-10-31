package ca.ulaval.glo4003.trotti.order.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.communication.domain.services.NotificationService;
import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;
import ca.ulaval.glo4003.trotti.order.application.CartApplicationService;
import ca.ulaval.glo4003.trotti.order.application.OrderApplicationService;
import ca.ulaval.glo4003.trotti.order.application.PassMapper;
import ca.ulaval.glo4003.trotti.order.application.TransactionMapper;
import ca.ulaval.glo4003.trotti.order.domain.factories.OrderFactory;
import ca.ulaval.glo4003.trotti.order.domain.factories.PassFactory;
import ca.ulaval.glo4003.trotti.order.domain.repositories.BuyerRepository;
import ca.ulaval.glo4003.trotti.order.domain.repositories.PassRepository;
import ca.ulaval.glo4003.trotti.order.domain.services.InvoiceNotificationService;
import ca.ulaval.glo4003.trotti.payment.domain.entities.invoice.Invoice;
import ca.ulaval.glo4003.trotti.payment.domain.factories.PaymentMethodFactory;
import ca.ulaval.glo4003.trotti.payment.domain.services.PaymentService;
import ca.ulaval.glo4003.trotti.payment.domain.services.TransactionNotificationService;
import ca.ulaval.glo4003.trotti.payment.domain.values.transaction.Transaction;

public class OrderApplicationServiceLoader extends Bootstrapper {

    @Override
    public void load() {
        loadOrderApplicationService();
        loadCartApplicationService();
    }

    private void loadOrderApplicationService() {
        NotificationService<Invoice> invoiceNotificationService =
                this.resourceLocator.resolve(InvoiceNotificationService.class);
        NotificationService<Transaction> transactionNotificationService =
                this.resourceLocator.resolve(TransactionNotificationService.class);
        TransactionMapper transactionMapper = this.resourceLocator.resolve(TransactionMapper.class);
        PaymentMethodFactory paymentMethodFactory =
                this.resourceLocator.resolve(PaymentMethodFactory.class);
        PaymentService paymentService = this.resourceLocator.resolve(PaymentService.class);
        BuyerRepository buyerRepository = this.resourceLocator.resolve(BuyerRepository.class);
        PassRepository passRepository = this.resourceLocator.resolve(PassRepository.class);
        OrderFactory orderFactory = this.resourceLocator.resolve(OrderFactory.class);

        OrderApplicationService orderApplicationService = new OrderApplicationService(
                buyerRepository, passRepository, paymentMethodFactory, orderFactory, paymentService,
                transactionMapper, transactionNotificationService, invoiceNotificationService);
        this.resourceLocator.register(OrderApplicationService.class, orderApplicationService);
    }

    private void loadCartApplicationService() {
        BuyerRepository buyerRepository = this.resourceLocator.resolve(BuyerRepository.class);
        PassMapper passMapper = this.resourceLocator.resolve(PassMapper.class);
        PassFactory passFactory = this.resourceLocator.resolve(PassFactory.class);

        CartApplicationService cartApplicationService =
                new CartApplicationService(buyerRepository, passMapper, passFactory);
        this.resourceLocator.register(CartApplicationService.class, cartApplicationService);
    }
}
