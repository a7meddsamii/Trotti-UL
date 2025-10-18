package ca.ulaval.glo4003.trotti.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.application.account.AccountApplicationService;
import ca.ulaval.glo4003.trotti.application.order.CartApplicationService;
import ca.ulaval.glo4003.trotti.application.order.OrderApplicationService;
import ca.ulaval.glo4003.trotti.application.order.mappers.PassMapper;
import ca.ulaval.glo4003.trotti.application.order.mappers.TransactionMapper;
import ca.ulaval.glo4003.trotti.application.trip.RidePermitActivationApplicationService;
import ca.ulaval.glo4003.trotti.application.trip.mappers.RidePermitMapper;
import ca.ulaval.glo4003.trotti.domain.account.factories.AccountFactory;
import ca.ulaval.glo4003.trotti.domain.account.repositories.AccountRepository;
import ca.ulaval.glo4003.trotti.domain.authentication.services.AuthenticationService;
import ca.ulaval.glo4003.trotti.domain.commons.communication.services.NotificationService;
import ca.ulaval.glo4003.trotti.domain.commons.payment.services.PaymentService;
import ca.ulaval.glo4003.trotti.domain.commons.payment.services.TransactionNotificationService;
import ca.ulaval.glo4003.trotti.domain.commons.payment.values.transaction.Transaction;
import ca.ulaval.glo4003.trotti.domain.order.entities.invoice.Invoice;
import ca.ulaval.glo4003.trotti.domain.order.factories.OrderFactory;
import ca.ulaval.glo4003.trotti.domain.order.factories.PassFactory;
import ca.ulaval.glo4003.trotti.domain.order.factories.PaymentMethodFactory;
import ca.ulaval.glo4003.trotti.domain.order.repositories.BuyerRepository;
import ca.ulaval.glo4003.trotti.domain.order.repositories.PassRepository;
import ca.ulaval.glo4003.trotti.domain.order.services.InvoiceNotificationService;
import ca.ulaval.glo4003.trotti.domain.trip.entities.RidePermit;
import ca.ulaval.glo4003.trotti.domain.trip.gateway.RidePermitHistoryGateway;
import ca.ulaval.glo4003.trotti.domain.trip.repositories.TravelerRepository;
import ca.ulaval.glo4003.trotti.domain.trip.services.EmployeeRidePermitService;
import ca.ulaval.glo4003.trotti.domain.trip.services.RidePermitNotificationService;
import java.util.List;

public class ApplicationServiceLoader extends ResourceLoader {
    @Override
    public void load() {
        loadAccountService();
        loadOrderApplicationService();
        loadRidePermitActivationApplicationService();
        loadCartApplicationService();
    }

    private void loadAccountService() {
        AccountRepository accountRepository = this.resourceLocator.resolve(AccountRepository.class);
        AuthenticationService authenticationService =
                this.resourceLocator.resolve(AuthenticationService.class);
        AccountFactory accountFactory = this.resourceLocator.resolve(AccountFactory.class);
        AccountApplicationService accountApplicationService = new AccountApplicationService(
                accountRepository, authenticationService, accountFactory);
        this.resourceLocator.register(AccountApplicationService.class, accountApplicationService);
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

    private void loadRidePermitActivationApplicationService() {
        TravelerRepository travelerRepository =
                this.resourceLocator.resolve(TravelerRepository.class);
        RidePermitHistoryGateway ridePermitHistoryGateway =
                this.resourceLocator.resolve(RidePermitHistoryGateway.class);
        NotificationService<List<RidePermit>> notificationService =
                this.resourceLocator.resolve(RidePermitNotificationService.class);
        RidePermitMapper ridePermitMapper = this.resourceLocator.resolve(RidePermitMapper.class);
        EmployeeRidePermitService employeeRidePermitService =
                this.resourceLocator.resolve(EmployeeRidePermitService.class);

        RidePermitActivationApplicationService ridePermitActivationService =
                new RidePermitActivationApplicationService(travelerRepository,
                        ridePermitHistoryGateway, notificationService, employeeRidePermitService,
                        ridePermitMapper);
        this.resourceLocator.register(RidePermitActivationApplicationService.class,
                ridePermitActivationService);
    }
}
