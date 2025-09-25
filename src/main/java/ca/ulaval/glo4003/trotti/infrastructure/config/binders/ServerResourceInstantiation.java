package ca.ulaval.glo4003.trotti.infrastructure.config.binders;

import ca.ulaval.glo4003.trotti.api.account.AccountController;
import ca.ulaval.glo4003.trotti.api.account.AuthentificationController;
import ca.ulaval.glo4003.trotti.application.account.AccountService;
import ca.ulaval.glo4003.trotti.application.mapper.AccountMapper;
import ca.ulaval.glo4003.trotti.domain.account.AccountFactory;
import ca.ulaval.glo4003.trotti.domain.account.PasswordHasher;
import ca.ulaval.glo4003.trotti.domain.account.authentication.AuthenticationService;
import ca.ulaval.glo4003.trotti.domain.account.repository.AccountRepository;
import ca.ulaval.glo4003.trotti.infrastructure.authentication.JwtAuthenticationServiceAdapter;
import ca.ulaval.glo4003.trotti.infrastructure.authentication.argon2.Argon2PasswordHasherAdapter;
import ca.ulaval.glo4003.trotti.infrastructure.config.ServerResourceLocator;
import ca.ulaval.glo4003.trotti.infrastructure.repository.UserInMemoryRepository;
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

    private void loadAuthenticatorResource() {
        try {
            Duration expirationDuration = Duration.parse(System.getenv(EXPIRATION_DURATION));
             authenticationService = new JwtAuthenticationServiceAdapter(
                    DEFAULT_EXPIRATION, SEVER_CLOCK, SECRET_KEY);
            locator.register(AuthenticationService.class, authenticationService);
            LOGGER.info("Token expiration duration set to {}", DurationFormatUtils
                    .formatDuration(expirationDuration.toMillis(), "H'h' m'm' s's'"));
        } catch (DateTimeParseException | NullPointerException exception) {
            LOGGER.error("Invalid or missing token expiration duration environment variable",
                    exception);
            throw exception;
        }
    }

    public void initiate() {
        if (resourcesCreated) {
            return;
        }

        loadAuthenticatorResource();
        resourcesCreated = true;
    }
}
