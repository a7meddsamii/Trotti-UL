package ca.ulaval.glo4003.trotti.infrastructure.config.binders;

import ca.ulaval.glo4003.trotti.application.account.AccountApplicationService;
import ca.ulaval.glo4003.trotti.domain.account.AccountFactory;
import ca.ulaval.glo4003.trotti.domain.account.PasswordHasher;
import ca.ulaval.glo4003.trotti.domain.account.authentication.AuthenticationService;
import ca.ulaval.glo4003.trotti.domain.account.repository.AccountRepository;
import ca.ulaval.glo4003.trotti.infrastructure.authentication.JwtAuthenticationServiceAdapter;
import ca.ulaval.glo4003.trotti.infrastructure.authentication.argon2.Argon2PasswordHasherAdapter;
import ca.ulaval.glo4003.trotti.infrastructure.config.ServerResourceLocator;
import io.jsonwebtoken.Jwts;
import java.time.Clock;
import java.time.Duration;
import java.time.format.DateTimeParseException;
import javax.crypto.SecretKey;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerResourceInstantiation {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerResourceInstantiation.class);
    private static final String EXPIRATION_DURATION = "TOKEN_EXPIRATION_DURATION";
    private static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();
    private static final Duration DEFAULT_EXPIRATION = Duration.ofMinutes(60);
    public static final int HASHER_MEMORY_COST = 65536;
    public static final int HASHER_ITERATIONS = 3;
    public static final int HASHER_NUMBER_OF_THREADS = 1;
    public static final Clock SEVER_CLOCK = Clock.systemDefaultZone();

    private static ServerResourceInstantiation instance;
    private final ServerResourceLocator locator;
    private boolean resourcesCreated;

    private PasswordHasher hasher;
    private AccountRepository accountRepository;
    private AccountFactory accountFactory;

    private AuthenticationService authenticationService;
    private AccountApplicationService accountApplicationService;

    public static ServerResourceInstantiation getInstance() {
        if (instance != null) {
            return instance;
        }

        return instance = new ServerResourceInstantiation();
    }

    private ServerResourceInstantiation() {
        this.locator = ServerResourceLocator.getInstance();
        this.resourcesCreated = false;
    }

    private void loadAuthenticationService() {
        try {
            Duration expirationDuration = Duration.parse(System.getProperty(EXPIRATION_DURATION));
            authenticationService = new JwtAuthenticationServiceAdapter(DEFAULT_EXPIRATION,
                    SEVER_CLOCK, SECRET_KEY);
            locator.register(AuthenticationService.class, authenticationService);
            LOGGER.info("Token expiration duration set to {}", DurationFormatUtils
                    .formatDuration(expirationDuration.toMillis(), "H'h' m'm' s's'"));
        } catch (DateTimeParseException | NullPointerException exception) {
            LOGGER.error("Invalid or missing token expiration duration environment variable",
                    exception);
            throw exception;
        }
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

    public void initiate() {
        if (resourcesCreated) {
            return;
        }

        loadAuthenticationService();
        loadPasswordHasher();
        loadAccountFactory();
        loadAccountService();
        resourcesCreated = true;
    }
}
