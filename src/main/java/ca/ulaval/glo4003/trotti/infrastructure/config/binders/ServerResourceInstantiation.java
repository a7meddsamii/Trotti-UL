package ca.ulaval.glo4003.trotti.infrastructure.config.binders;

import ca.ulaval.glo4003.trotti.application.account.AccountApplicationService;
import ca.ulaval.glo4003.trotti.application.order.OrderApplicationService;
import ca.ulaval.glo4003.trotti.application.order.mappers.PassMapper;
import ca.ulaval.glo4003.trotti.application.trip.RidePermitActivationApplicationService;
import ca.ulaval.glo4003.trotti.domain.account.AccountFactory;
import ca.ulaval.glo4003.trotti.domain.account.repository.AccountRepository;
import ca.ulaval.glo4003.trotti.domain.account.services.PasswordHasher;
import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.authentication.AuthenticationService;
import ca.ulaval.glo4003.trotti.domain.communication.EmailService;
import ca.ulaval.glo4003.trotti.domain.order.OrderFactory;
import ca.ulaval.glo4003.trotti.domain.order.repository.BuyerRepository;
import ca.ulaval.glo4003.trotti.domain.payment.services.PaymentService;
import ca.ulaval.glo4003.trotti.domain.trip.NotificationService;
import ca.ulaval.glo4003.trotti.domain.trip.repository.TravelerRepository;
import ca.ulaval.glo4003.trotti.infrastructure.account.mappers.AccountPersistenceMapper;
import ca.ulaval.glo4003.trotti.infrastructure.account.repository.AccountRecord;
import ca.ulaval.glo4003.trotti.infrastructure.account.repository.InMemoryAccountRepository;
import ca.ulaval.glo4003.trotti.infrastructure.account.services.Argon2PasswordHasherAdapter;
import ca.ulaval.glo4003.trotti.infrastructure.authentication.JwtAuthenticationServiceAdapter;
import ca.ulaval.glo4003.trotti.infrastructure.communication.JakartaEmailServiceAdapter;
import ca.ulaval.glo4003.trotti.infrastructure.config.JakartaMailServiceConfiguration;
import ca.ulaval.glo4003.trotti.infrastructure.config.ServerResourceLocator;
import ca.ulaval.glo4003.trotti.infrastructure.config.providers.SessionProvider;
import ca.ulaval.glo4003.trotti.infrastructure.order.repository.BuyerRecord;
import ca.ulaval.glo4003.trotti.infrastructure.persistence.UserInMemoryDatabase;
import ca.ulaval.glo4003.trotti.infrastructure.sessions.mappers.SessionMapper;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;
import java.nio.file.Path;
import java.time.Clock;
import java.time.Duration;
import java.time.format.DateTimeParseException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

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
    private PassMapper passMapper;
    private OrderFactory orderFactory;
    private PaymentService paymentService;

    private AuthenticationService authenticationService;
    private AccountApplicationService accountApplicationService;

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
        UserInMemoryDatabase userInMemoryDatabase =
                new UserInMemoryDatabase(accountTable, buyerTable);
        AccountPersistenceMapper accountMapper = new AccountPersistenceMapper();
        accountRepository = new InMemoryAccountRepository(userInMemoryDatabase, accountMapper);
        locator.register(AccountRepository.class, accountRepository);
        locator.register(BuyerRepository.class, buyerRepository);
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
        OrderApplicationService orderApplicationService = new OrderApplicationService(
                buyerRepository, orderFactory, paymentService, emailService);
        locator.register(OrderApplicationService.class, orderApplicationService);
    }
	
	private void loadRidePermitActivationService() {
		NotificationService notificationService = new NotificationService(emailService);
		
		RidePermitActivationApplicationService ridePermitActivationService = new RidePermitActivationApplicationService(
				travelerRepository,
				
		);
	}
	
    public void initiate() {
        if (resourcesCreated) {
            return;
        }

        loadAuthenticationService();
        loadEmailSender();
        loadPasswordHasher();
        loadUserRepositories();
        loadSessionProvider();
        loadAccountFactory();
        loadAccountService();
        loadPassMapper();
        loadOrderFactory();
        loadPaymentService();
        loadOrderService();
		loadRidePermitActivationService();
        resourcesCreated = true;
    }
}
