package ca.ulaval.glo4003.trotti.config.loaders;

import ca.ulaval.glo4003.trotti.account.application.AccountApplicationService;
import ca.ulaval.glo4003.trotti.order.application.CartApplicationService;
import ca.ulaval.glo4003.trotti.order.application.OrderApplicationService;
import ca.ulaval.glo4003.trotti.order.application.mappers.PassMapper;
import ca.ulaval.glo4003.trotti.order.application.mappers.TransactionMapper;
import ca.ulaval.glo4003.trotti.trip.application.RidePermitActivationApplicationService;
import ca.ulaval.glo4003.trotti.trip.application.TripApplicationService;
import ca.ulaval.glo4003.trotti.trip.application.UnlockCodeApplicationService;
import ca.ulaval.glo4003.trotti.trip.application.mappers.RidePermitMapper;
import ca.ulaval.glo4003.trotti.account.domain.factories.AccountFactory;
import ca.ulaval.glo4003.trotti.account.domain.repositories.AccountRepository;
import ca.ulaval.glo4003.trotti.account.domain.services.AuthenticationService;
import ca.ulaval.glo4003.trotti.communication.domain.services.NotificationService;
import ca.ulaval.glo4003.trotti.payment.domain.services.PaymentService;
import ca.ulaval.glo4003.trotti.payment.domain.services.TransactionNotificationService;
import ca.ulaval.glo4003.trotti.payment.domain.values.transaction.Transaction;
import ca.ulaval.glo4003.trotti.payment.domain.entities.invoice.Invoice;
import ca.ulaval.glo4003.trotti.order.domain.factories.OrderFactory;
import ca.ulaval.glo4003.trotti.order.domain.factories.PassFactory;
import ca.ulaval.glo4003.trotti.order.domain.factories.PaymentMethodFactory;
import ca.ulaval.glo4003.trotti.order.domain.repositories.BuyerRepository;
import ca.ulaval.glo4003.trotti.order.domain.repositories.PassRepository;
import ca.ulaval.glo4003.trotti.order.domain.services.InvoiceNotificationService;
import ca.ulaval.glo4003.trotti.trip.domain.services.UnlockCodeNotificationService;
import ca.ulaval.glo4003.trotti.trip.domain.entities.RidePermit;
import ca.ulaval.glo4003.trotti.trip.domain.entities.UnlockCode;
import ca.ulaval.glo4003.trotti.trip.domain.gateway.RidePermitHistoryGateway;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.ScooterRepository;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.StationRepository;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.TravelerRepository;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.TripRepository;
import ca.ulaval.glo4003.trotti.trip.domain.services.EmployeeRidePermitService;
import ca.ulaval.glo4003.trotti.trip.domain.services.RidePermitNotificationService;
import ca.ulaval.glo4003.trotti.trip.domain.services.UnlockCodeService;
import java.time.Clock;
import java.util.List;

public class ApplicationServiceLoader extends Bootstrapper {
    @Override
    public void load() {
        loadAccountApplicationService();
        loadOrderApplicationService();
        loadRidePermitActivationApplicationService();
        loadCartApplicationService();
        loadUnlockCodeApplicationService();
        loadTripApplicationService();
    }

    private void loadAccountApplicationService() {
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

    private void loadUnlockCodeApplicationService() {
        UnlockCodeService unlockCodeService = this.resourceLocator.resolve(UnlockCodeService.class);
        TravelerRepository travelerRepository =
                this.resourceLocator.resolve(TravelerRepository.class);
        NotificationService<UnlockCode> notificationService =
                this.resourceLocator.resolve(UnlockCodeNotificationService.class);

        UnlockCodeApplicationService unlockCodeApplicationService =
                new UnlockCodeApplicationService(unlockCodeService, travelerRepository,
                        notificationService);

        this.resourceLocator.register(UnlockCodeApplicationService.class,
                unlockCodeApplicationService);
    }

    private void loadTripApplicationService() {
        TravelerRepository travelerRepository =
                this.resourceLocator.resolve(TravelerRepository.class);
        StationRepository stationRepository = this.resourceLocator.resolve(StationRepository.class);
        ScooterRepository scooterRepository = this.resourceLocator.resolve(ScooterRepository.class);
        TripRepository tripRepository = this.resourceLocator.resolve(TripRepository.class);
        UnlockCodeService unlockCodeService = this.resourceLocator.resolve(UnlockCodeService.class);
        Clock clock = this.resourceLocator.resolve(Clock.class);
        TripApplicationService tripApplicationService =
                new TripApplicationService(travelerRepository, stationRepository, scooterRepository,
                        tripRepository, unlockCodeService, clock);
        this.resourceLocator.register(TripApplicationService.class, tripApplicationService);
    }

}
