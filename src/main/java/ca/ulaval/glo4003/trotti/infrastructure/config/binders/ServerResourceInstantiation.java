package ca.ulaval.glo4003.trotti.infrastructure.config.binders;

import ca.ulaval.glo4003.trotti.api.account.mappers.AccountApiMapper;
import ca.ulaval.glo4003.trotti.api.order.mappers.OrderApiMapper;
import ca.ulaval.glo4003.trotti.api.order.mappers.PassApiMapper;
import ca.ulaval.glo4003.trotti.api.account.resources.AccountResource;
import ca.ulaval.glo4003.trotti.api.account.resources.AuthenticationResource;
import ca.ulaval.glo4003.trotti.api.order.resources.CartResource;
import ca.ulaval.glo4003.trotti.api.order.resources.OrderResource;
import ca.ulaval.glo4003.trotti.application.account.AccountApplicationService;
import ca.ulaval.glo4003.trotti.application.order.CartApplicationService;
import ca.ulaval.glo4003.trotti.application.order.OrderApplicationService;
import ca.ulaval.glo4003.trotti.application.order.mappers.PassMapper;
import ca.ulaval.glo4003.trotti.application.order.mappers.TransactionMapper;
import ca.ulaval.glo4003.trotti.application.trip.RidePermitActivationApplicationService;
import ca.ulaval.glo4003.trotti.domain.account.AccountFactory;
import ca.ulaval.glo4003.trotti.domain.account.repository.AccountRepository;
import ca.ulaval.glo4003.trotti.domain.account.services.PasswordHasher;
import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.authentication.AuthenticationService;
import ca.ulaval.glo4003.trotti.domain.communication.EmailService;
import ca.ulaval.glo4003.trotti.domain.communication.NotificationService;
import ca.ulaval.glo4003.trotti.domain.order.Invoice;
import ca.ulaval.glo4003.trotti.domain.order.OrderFactory;
import ca.ulaval.glo4003.trotti.domain.order.PassFactory;
import ca.ulaval.glo4003.trotti.domain.order.PaymentMethodFactory;
import ca.ulaval.glo4003.trotti.domain.order.repository.BuyerRepository;
import ca.ulaval.glo4003.trotti.domain.order.repository.PassRepository;
import ca.ulaval.glo4003.trotti.domain.payment.security.DataCodec;
import ca.ulaval.glo4003.trotti.domain.payment.services.InvoiceFormatService;
import ca.ulaval.glo4003.trotti.domain.payment.services.InvoiceNotificationService;
import ca.ulaval.glo4003.trotti.domain.payment.services.PaymentService;
import ca.ulaval.glo4003.trotti.domain.payment.services.TransactionNotificationService;
import ca.ulaval.glo4003.trotti.domain.payment.values.Transaction;
import ca.ulaval.glo4003.trotti.domain.trip.RidePermit;
import ca.ulaval.glo4003.trotti.domain.trip.RidePermitNotificationService;
import ca.ulaval.glo4003.trotti.domain.trip.repository.TravelerRepository;
import ca.ulaval.glo4003.trotti.domain.trip.services.RidePermitHistoryGateway;
import ca.ulaval.glo4003.trotti.infrastructure.account.mappers.AccountPersistenceMapper;
import ca.ulaval.glo4003.trotti.infrastructure.account.repository.AccountRecord;
import ca.ulaval.glo4003.trotti.infrastructure.account.repository.InMemoryAccountRepository;
import ca.ulaval.glo4003.trotti.infrastructure.account.services.Argon2PasswordHasherAdapter;
import ca.ulaval.glo4003.trotti.infrastructure.authentication.JwtAuthenticationServiceAdapter;
import ca.ulaval.glo4003.trotti.infrastructure.communication.JakartaEmailServiceAdapter;
import ca.ulaval.glo4003.trotti.infrastructure.config.JakartaMailServiceConfiguration;
import ca.ulaval.glo4003.trotti.infrastructure.config.ServerResourceLocator;
import ca.ulaval.glo4003.trotti.infrastructure.config.providers.SessionProvider;
import ca.ulaval.glo4003.trotti.infrastructure.order.mappers.BuyerPersistenceMapper;
import ca.ulaval.glo4003.trotti.infrastructure.order.mappers.PassPersistenceMapper;
import ca.ulaval.glo4003.trotti.infrastructure.order.repository.InMemoryBuyerRepository;
import ca.ulaval.glo4003.trotti.infrastructure.order.repository.InMemoryPassRepository;
import ca.ulaval.glo4003.trotti.infrastructure.order.repository.records.BuyerRecord;
import ca.ulaval.glo4003.trotti.infrastructure.payment.services.TextInvoiceFormatServiceAdapter;
import ca.ulaval.glo4003.trotti.infrastructure.payment.services.security.AesDataCodecAdapter;
import ca.ulaval.glo4003.trotti.infrastructure.persistence.UserInMemoryDatabase;
import ca.ulaval.glo4003.trotti.infrastructure.sessions.mappers.SessionMapper;
import ca.ulaval.glo4003.trotti.infrastructure.trip.mappers.TravelerPersistenceMapper;
import ca.ulaval.glo4003.trotti.infrastructure.trip.records.TravelerRecord;
import ca.ulaval.glo4003.trotti.infrastructure.trip.repository.InMemoryTravelerRepository;
import ca.ulaval.glo4003.trotti.infrastructure.trip.services.RidePermitHistoryGatewayAdapter;
import io.jsonwebtoken.Jwts;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.time.Clock;
import java.time.Duration;
import java.time.format.DateTimeParseException;
import java.util.List;
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
    private static final String SEMESTER_DATA_FILE_PATH = "/app/data/semesters-252627.json";

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
                    authenticatorClock, SECRET_KEY);
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

    private void loadPassRepository() {
        PassPersistenceMapper passMapper = new PassPersistenceMapper();
        passRepository = new InMemoryPassRepository(passMapper);
        locator.register(PassPersistenceMapper.class, passMapper);
        locator.register(PassRepository.class, passRepository);
    }

    private void loadSessionProvider() {
        SessionMapper sessionMapper = new SessionMapper();
        Path resourcePath = Path.of(SEMESTER_DATA_FILE_PATH);
        SessionProvider.initialize(resourcePath, sessionMapper);
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
        RidePermitActivationApplicationService ridePermitActivationService =
                new RidePermitActivationApplicationService(travelerRepository,
                        ridePermitHistoryGateway, notificationService);
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

    private void loadCartResource() {
        PassApiMapper passApiMapper = new PassApiMapper();
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

    public void initiate() {
        if (resourcesCreated) {
            return;
        }

        loadAuthenticationService();
        loadEmailSender();
        loadPasswordHasher();
        loadUserRepositories();
        loadPassRepository();
        loadSessionProvider();
        loadAccountFactory();
        loadAccountService();
        loadPassMapper();
        loadOrderFactory();
        loadPaymentService();
        loadOrderService();
        loadRidePermitActivationService();
        loadAccountMapper();
        loadAccountResource();
        loadCartResource();
        loadOrderResource();
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
