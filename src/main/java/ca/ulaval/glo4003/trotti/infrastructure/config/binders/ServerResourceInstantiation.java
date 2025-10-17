package ca.ulaval.glo4003.trotti.infrastructure.config.binders;

import ca.ulaval.glo4003.trotti.api.account.controllers.AccountResource;
import ca.ulaval.glo4003.trotti.api.account.mappers.AccountApiMapper;
import ca.ulaval.glo4003.trotti.api.authentication.controllers.AuthenticationResource;
import ca.ulaval.glo4003.trotti.api.order.controllers.CartResource;
import ca.ulaval.glo4003.trotti.api.order.controllers.OrderResource;
import ca.ulaval.glo4003.trotti.api.order.mappers.OrderApiMapper;
import ca.ulaval.glo4003.trotti.api.order.mappers.PassApiMapper;
import ca.ulaval.glo4003.trotti.api.trip.controllers.TravelerResource;
import ca.ulaval.glo4003.trotti.api.trip.controllers.UnlockCodeResource;
import ca.ulaval.glo4003.trotti.api.trip.mappers.UnlockCodeApiMapper;
import ca.ulaval.glo4003.trotti.application.account.AccountApplicationService;
import ca.ulaval.glo4003.trotti.application.order.CartApplicationService;
import ca.ulaval.glo4003.trotti.application.order.OrderApplicationService;
import ca.ulaval.glo4003.trotti.application.order.mappers.PassMapper;
import ca.ulaval.glo4003.trotti.application.order.mappers.TransactionMapper;
import ca.ulaval.glo4003.trotti.application.trip.RidePermitActivationApplicationService;
import ca.ulaval.glo4003.trotti.application.trip.UnlockCodeApplicationService;
import ca.ulaval.glo4003.trotti.application.trip.mappers.RidePermitMapper;
import ca.ulaval.glo4003.trotti.application.trip.mappers.UnlockCodeMapper;
import ca.ulaval.glo4003.trotti.domain.account.factories.AccountFactory;
import ca.ulaval.glo4003.trotti.domain.account.repositories.AccountRepository;
import ca.ulaval.glo4003.trotti.domain.account.services.PasswordHasher;
import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.authentication.services.AuthenticationService;
import ca.ulaval.glo4003.trotti.domain.commons.EmployeeRegistry;
import ca.ulaval.glo4003.trotti.domain.commons.SessionRegistry;
import ca.ulaval.glo4003.trotti.domain.commons.communication.services.EmailService;
import ca.ulaval.glo4003.trotti.domain.commons.communication.services.NotificationService;
import ca.ulaval.glo4003.trotti.domain.commons.payment.security.DataCodec;
import ca.ulaval.glo4003.trotti.domain.commons.payment.services.PaymentService;
import ca.ulaval.glo4003.trotti.domain.commons.payment.services.TransactionNotificationService;
import ca.ulaval.glo4003.trotti.domain.commons.payment.values.transaction.Transaction;
import ca.ulaval.glo4003.trotti.domain.order.entities.invoice.Invoice;
import ca.ulaval.glo4003.trotti.domain.order.factories.OrderFactory;
import ca.ulaval.glo4003.trotti.domain.order.factories.PassFactory;
import ca.ulaval.glo4003.trotti.domain.order.factories.PaymentMethodFactory;
import ca.ulaval.glo4003.trotti.domain.order.repositories.BuyerRepository;
import ca.ulaval.glo4003.trotti.domain.order.repositories.PassRepository;
import ca.ulaval.glo4003.trotti.domain.order.services.InvoiceFormatService;
import ca.ulaval.glo4003.trotti.domain.order.services.InvoiceNotificationService;
import ca.ulaval.glo4003.trotti.domain.trip.communication.services.UnlockCodeNotificationService;
import ca.ulaval.glo4003.trotti.domain.trip.entities.RidePermit;
import ca.ulaval.glo4003.trotti.domain.trip.entities.UnlockCode;
import ca.ulaval.glo4003.trotti.domain.trip.gateway.RidePermitHistoryGateway;
import ca.ulaval.glo4003.trotti.domain.trip.repositories.TravelerRepository;
import ca.ulaval.glo4003.trotti.domain.trip.services.EmployeeRidePermitService;
import ca.ulaval.glo4003.trotti.domain.trip.services.RidePermitNotificationService;
import ca.ulaval.glo4003.trotti.domain.trip.services.UnlockCodeService;
import ca.ulaval.glo4003.trotti.domain.trip.store.UnlockCodeStore;
import ca.ulaval.glo4003.trotti.infrastructure.account.mappers.AccountPersistenceMapper;
import ca.ulaval.glo4003.trotti.infrastructure.account.repositories.InMemoryAccountRepository;
import ca.ulaval.glo4003.trotti.infrastructure.account.repositories.records.AccountRecord;
import ca.ulaval.glo4003.trotti.infrastructure.account.services.Argon2PasswordHasherAdapter;
import ca.ulaval.glo4003.trotti.infrastructure.authentication.JwtAuthenticationServiceAdapter;
import ca.ulaval.glo4003.trotti.infrastructure.commons.communication.services.JakartaEmailServiceAdapter;
import ca.ulaval.glo4003.trotti.infrastructure.commons.payment.security.AesDataCodecAdapter;
import ca.ulaval.glo4003.trotti.infrastructure.commons.sessions.mappers.SessionMapper;
import ca.ulaval.glo4003.trotti.infrastructure.config.JakartaMailServiceConfiguration;
import ca.ulaval.glo4003.trotti.infrastructure.config.ServerResourceLocator;
import ca.ulaval.glo4003.trotti.infrastructure.config.datafactories.AccountDevDataFactory;
import ca.ulaval.glo4003.trotti.infrastructure.config.providers.EmployeeIdulCsvProvider;
import ca.ulaval.glo4003.trotti.infrastructure.config.providers.SessionProvider;
import ca.ulaval.glo4003.trotti.infrastructure.order.mappers.BuyerPersistenceMapper;
import ca.ulaval.glo4003.trotti.infrastructure.order.mappers.PassPersistenceMapper;
import ca.ulaval.glo4003.trotti.infrastructure.order.repositories.InMemoryBuyerRepository;
import ca.ulaval.glo4003.trotti.infrastructure.order.repositories.InMemoryPassRepository;
import ca.ulaval.glo4003.trotti.infrastructure.order.repositories.records.BuyerRecord;
import ca.ulaval.glo4003.trotti.infrastructure.order.services.TextInvoiceFormatServiceAdapter;
import ca.ulaval.glo4003.trotti.infrastructure.persistence.inmemory.UserInMemoryDatabase;
import ca.ulaval.glo4003.trotti.infrastructure.trip.gateway.RidePermitHistoryGatewayAdapter;
import ca.ulaval.glo4003.trotti.infrastructure.trip.mappers.TravelerPersistenceMapper;
import ca.ulaval.glo4003.trotti.infrastructure.trip.repositories.InMemoryTravelerRepository;
import ca.ulaval.glo4003.trotti.infrastructure.trip.repositories.records.TravelerRecord;
import ca.ulaval.glo4003.trotti.infrastructure.trip.store.GuavaUnlockCodeStore;
import io.jsonwebtoken.Jwts;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.time.Clock;
import java.time.Duration;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerResourceInstantiation {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerResourceInstantiation.class);
    private static final String EXPIRATION_DURATION = "TOKEN_EXPIRATION_DURATION";
    private static final String EMAIL_USER = "STMP_USER";
    private static final String EMAIL_PASSWORD = "STMP_PASS";
    private static final String EMAIL_HOST = "STMP_HOST";
    private static final String EMAIL_PORT = "STMP_PORT";
    private static final Duration DEFAULT_TOKEN_EXPIRATION = Duration.ofMinutes(60);
    private static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();
    private static final int HASHER_MEMORY_COST = 65536;
    private static final int HASHER_ITERATIONS = 3;
    private static final int HASHER_NUMBER_OF_THREADS = 1;
    private static final Clock SEVER_CLOCK = Clock.systemDefaultZone();
    private static final Path SEMESTER_DATA_FILE_PATH = Path.of("/app/data/semesters-252627.json");
    private static final Path EMPLOYEE_IDUL_CSV_PATH = Path.of("/app/data/Employe.e.s.csv");

    private static ServerResourceInstantiation instance;
    private final ServerResourceLocator locator;
    private boolean resourcesCreated;

    private EmailService emailService;
    private PasswordHasher hasher;

    private AccountRepository accountRepository;
    private AccountFactory accountFactory;
    private BuyerRepository buyerRepository;
    private TravelerRepository travelerRepository;
    private PassRepository passRepository;
    private PassMapper passMapper;
    private AccountApiMapper accountApiMapper;
    private OrderFactory orderFactory;
    private PassFactory passFactory;
    private PaymentMethodFactory paymentMethodFactory;
    private PaymentService paymentService;
    private AuthenticationService authenticationService;
    private AccountApplicationService accountApplicationService;
    private OrderApplicationService orderApplicationService;
    private UnlockCodeApplicationService unlockCodeApplicationService;
    private SessionRegistry sessionRegistry;
    private RidePermitActivationApplicationService ridePermitActivationService;
    private EmployeeRegistry employeeRegistry;

    public static ServerResourceInstantiation getInstance() {
        if (instance == null) {
            instance = new ServerResourceInstantiation();
        }

        return instance;
    }

    private ServerResourceInstantiation() {
        this.locator = ServerResourceLocator.getInstance();
        this.resourcesCreated = false;
    }

    private void loadAuthenticationService() {
        try {
            String durationValue = StringUtils.isEmpty(System.getenv(EXPIRATION_DURATION))
                    ? DEFAULT_TOKEN_EXPIRATION.toString()
                    : System.getenv(EXPIRATION_DURATION);
            Duration expirationDuration = Duration.parse(durationValue);
            Clock authenticatorClock = Clock.systemDefaultZone();
            authenticationService = new JwtAuthenticationServiceAdapter(expirationDuration,
                    authenticatorClock, SECRET_KEY, this.employeeRegistry);
            locator.register(AuthenticationService.class, authenticationService);
            LOGGER.info("Token expiration duration set to {}", DurationFormatUtils
                    .formatDuration(expirationDuration.toMillis(), "H'h' m'm' s's'"));
        } catch (DateTimeParseException | NullPointerException exception) {
            LOGGER.error("Invalid or missing token expiration duration environment variable",
                    exception);
            throw exception;
        }
    }

    private void loadUserRepositories() {
        ConcurrentMap<Idul, AccountRecord> accountTable = new ConcurrentHashMap<>();
        ConcurrentMap<Idul, BuyerRecord> buyerTable = new ConcurrentHashMap<>();
        ConcurrentMap<Idul, TravelerRecord> travelerTable = new ConcurrentHashMap<>();
        UserInMemoryDatabase userInMemoryDatabase =
                new UserInMemoryDatabase(accountTable, buyerTable, travelerTable);
        AccountPersistenceMapper accountMapper = new AccountPersistenceMapper();
        BuyerPersistenceMapper buyerMapper = new BuyerPersistenceMapper();
        TravelerPersistenceMapper travelerMapper = new TravelerPersistenceMapper();
        accountRepository = new InMemoryAccountRepository(userInMemoryDatabase, accountMapper);
        travelerRepository = new InMemoryTravelerRepository(userInMemoryDatabase, travelerMapper);
        buyerRepository = new InMemoryBuyerRepository(userInMemoryDatabase, buyerMapper);
        locator.register(AccountRepository.class, accountRepository);
        locator.register(BuyerRepository.class, buyerRepository);
        locator.register(TravelerRepository.class, travelerRepository);
    }

    private void loadDevData() {
        new AccountDevDataFactory(accountRepository, hasher).run();
    }

    private void loadPassRepository() {
        PassPersistenceMapper passMapper = new PassPersistenceMapper();
        passRepository = new InMemoryPassRepository(passMapper);
        locator.register(PassPersistenceMapper.class, passMapper);
        locator.register(PassRepository.class, passRepository);
    }

    private void loadSessionProvider() {
        SessionMapper sessionMapper = new SessionMapper();
        SessionProvider.initialize(SEMESTER_DATA_FILE_PATH, sessionMapper);
        sessionRegistry = new SessionRegistry(SessionProvider.getInstance().getSessions());
        locator.register(SessionRegistry.class, sessionRegistry);
    }

    private void loadEmailSender() {
        String username = System.getenv(EMAIL_USER);
        String password = System.getenv(EMAIL_PASSWORD);
        String host = System.getenv(EMAIL_HOST);
        String port = System.getenv(EMAIL_PORT);

        JakartaMailServiceConfiguration emailConfiguration =
                JakartaMailServiceConfiguration.create(username, password, host, port);
        emailService = new JakartaEmailServiceAdapter(emailConfiguration.connect());
        locator.register(EmailService.class, emailService);
    }

    private void loadPasswordHasher() {
        hasher = new Argon2PasswordHasherAdapter(HASHER_MEMORY_COST, HASHER_ITERATIONS,
                HASHER_NUMBER_OF_THREADS);
        locator.register(PasswordHasher.class, hasher);
    }

    private void loadAccountFactory() {
        accountFactory = new AccountFactory(SEVER_CLOCK);
        locator.register(AccountFactory.class, accountFactory);
    }

    private void loadAccountService() {
        accountApplicationService = new AccountApplicationService(accountRepository,
                authenticationService, accountFactory);
        locator.register(AccountApplicationService.class, accountApplicationService);
    }

    private void loadPassMapper() {
        passMapper = new PassMapper();
        locator.register(PassMapper.class, passMapper);
    }

    private void loadOrderFactory() {
        orderFactory = new OrderFactory();
        locator.register(OrderFactory.class, orderFactory);
    }

    private void loadPaymentService() {
        paymentService = new PaymentService();
        locator.register(PaymentService.class, paymentService);
    }

    private void loadOrderService() {
        InvoiceFormatService<String> invoiceFormatService = new TextInvoiceFormatServiceAdapter();
        NotificationService<Invoice> invoiceNotificationService =
                new InvoiceNotificationService(emailService, invoiceFormatService);
        NotificationService<Transaction> transactionNotificationService =
                new TransactionNotificationService(emailService);
        TransactionMapper transactionMapper = new TransactionMapper();
        DataCodec dataCodec = new AesDataCodecAdapter(generateSecretKey());
        paymentMethodFactory = new PaymentMethodFactory(dataCodec);
        orderApplicationService = new OrderApplicationService(buyerRepository, passRepository,
                paymentMethodFactory, orderFactory, paymentService, transactionMapper,
                transactionNotificationService, invoiceNotificationService);

        locator.register(OrderApplicationService.class, orderApplicationService);
    }

    private void loadRidePermitActivationService() {
        NotificationService<List<RidePermit>> notificationService =
                new RidePermitNotificationService(emailService);
        RidePermitHistoryGateway ridePermitHistoryGateway =
                new RidePermitHistoryGatewayAdapter(passRepository);
        EmployeeRidePermitService employeeRidePermitService =
                new EmployeeRidePermitService(employeeRegistry, sessionRegistry);
        RidePermitMapper ridePermitMapper = new RidePermitMapper();
        ridePermitActivationService = new RidePermitActivationApplicationService(travelerRepository,
                ridePermitHistoryGateway, notificationService, employeeRidePermitService,
                ridePermitMapper);
        locator.register(RidePermitHistoryGateway.class, ridePermitHistoryGateway);
        locator.register(RidePermitActivationApplicationService.class, ridePermitActivationService);
    }

    private void loadAccountMapper() {
        accountApiMapper = new AccountApiMapper(hasher);
        locator.register(AccountApiMapper.class, accountApiMapper);
    }

    private void loadAccountResource() {
        AccountResource accountResource =
                new AccountResource(accountApplicationService, accountApiMapper);
        AuthenticationResource authenticationResource =
                new AuthenticationResource(accountApplicationService);
        locator.register(AccountResource.class, accountResource);
        locator.register(AuthenticationResource.class, authenticationResource);
    }

    private void loadEmployeeIdulRegistry() {
        EmployeeIdulCsvProvider reader = new EmployeeIdulCsvProvider();
        Set<Idul> employeesIduls = reader.readFromClasspath(EMPLOYEE_IDUL_CSV_PATH);
        this.employeeRegistry = new EmployeeRegistry(employeesIduls);
        locator.register(EmployeeRegistry.class, employeeRegistry);
    }

    private void loadTravelerResource() {
        TravelerResource travelerResource =
                new TravelerResource(ridePermitActivationService, authenticationService);
        locator.register(TravelerResource.class, travelerResource);
    }

    private void loadCartResource() {
        PassApiMapper passApiMapper = new PassApiMapper(SessionProvider.getInstance());
        passFactory = new PassFactory();
        CartApplicationService cartApplicationService =
                new CartApplicationService(buyerRepository, passMapper, passFactory);
        CartResource cartResource = new CartResource(cartApplicationService,
                locator.resolve(AuthenticationService.class), passApiMapper);
        locator.register(CartResource.class, cartResource);
    }

    private void loadOrderResource() {
        OrderApiMapper orderApiMapper = new OrderApiMapper();
        OrderResource orderResource = new OrderResource(orderApplicationService,
                locator.resolve(AuthenticationService.class), orderApiMapper);
        locator.register(OrderResource.class, orderResource);
    }

    private void loadUnlockCodeApplicationService() {
        UnlockCodeStore unlockCodeStore = new GuavaUnlockCodeStore();
        NotificationService<UnlockCode> notificationService =
                new UnlockCodeNotificationService(emailService);
        locator.register(UnlockCodeStore.class, unlockCodeStore);
        unlockCodeApplicationService = new UnlockCodeApplicationService(
                new UnlockCodeService(unlockCodeStore, SEVER_CLOCK), travelerRepository, notificationService,
                new UnlockCodeMapper());
    }

    private void loadUnlockCodeRessource() {
        UnlockCodeResource unlockCodeResource =
                new UnlockCodeResource(locator.resolve(AuthenticationService.class),
                        unlockCodeApplicationService, new UnlockCodeApiMapper());
        locator.register(UnlockCodeResource.class, unlockCodeResource);
    }

    public void initiate() {
        if (resourcesCreated) {
            return;
        }

        loadEmployeeIdulRegistry();
        loadAuthenticationService();
        loadEmailSender();
        loadPasswordHasher();
        loadUserRepositories();
        loadDevData();
        loadPassRepository();
        loadSessionProvider();
        loadAccountFactory();
        loadAccountService();
        loadPassMapper();
        loadOrderFactory();
        loadPaymentService();
        loadOrderService();
        loadRidePermitActivationService();
        loadUnlockCodeApplicationService();
        loadAccountMapper();
        loadAccountResource();
        loadTravelerResource();
        loadCartResource();
        loadOrderResource();
        loadUnlockCodeRessource();
        resourcesCreated = true;
    }

    private SecretKey generateSecretKey() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(128);
            return keyGen.generateKey();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to generate secret key for tests", e);
        }
    }
}
