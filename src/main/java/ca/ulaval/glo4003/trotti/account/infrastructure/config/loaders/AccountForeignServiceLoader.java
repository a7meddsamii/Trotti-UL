package ca.ulaval.glo4003.trotti.account.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.account.domain.services.AuthenticationService;
import ca.ulaval.glo4003.trotti.account.domain.services.PasswordHasher;
import ca.ulaval.glo4003.trotti.account.infrastructure.services.Argon2PasswordHasherAdapter;
import ca.ulaval.glo4003.trotti.account.infrastructure.services.JwtAuthenticationServiceAdapter;
import ca.ulaval.glo4003.trotti.commons.domain.gateways.EmployeeRegistryGateway;
import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;
import io.jsonwebtoken.Jwts;
import java.time.Clock;
import java.time.Duration;
import java.time.format.DateTimeParseException;
import javax.crypto.SecretKey;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccountForeignServiceLoader extends Bootstrapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountForeignServiceLoader.class);
    private static final String EXPIRATION_DURATION = "TOKEN_EXPIRATION_DURATION";
    private static final Duration DEFAULT_TOKEN_EXPIRATION = Duration.ofMinutes(60);
    private static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();

    private static final int HASHER_MEMORY_COST = 65536;
    private static final int HASHER_ITERATIONS = 3;
    private static final int HASHER_NUMBER_OF_THREADS = 1;

    @Override
    public void load() {
        loadPasswordHasherService();
        loadAuthenticationService();
    }

    private void loadPasswordHasherService() {
        PasswordHasher hasher = new Argon2PasswordHasherAdapter(HASHER_MEMORY_COST,
                HASHER_ITERATIONS, HASHER_NUMBER_OF_THREADS);
        this.resourceLocator.register(PasswordHasher.class, hasher);
    }

    private void loadAuthenticationService() {
        try {
			EmployeeRegistryGateway employeeRegistryGateway =
                    this.resourceLocator.resolve(EmployeeRegistryGateway.class);

            String durationValue = StringUtils.defaultIfBlank(System.getenv(EXPIRATION_DURATION),
                    DEFAULT_TOKEN_EXPIRATION.toString());

            Duration expirationDuration = Duration.parse(durationValue);
            Clock authenticatorClock = this.resourceLocator.resolve(Clock.class);
            AuthenticationService authenticationService = new JwtAuthenticationServiceAdapter(
                    expirationDuration, authenticatorClock, SECRET_KEY, employeeRegistryGateway);

            this.resourceLocator.register(AuthenticationService.class, authenticationService);
            LOGGER.info("Token expiration duration set to {}", DurationFormatUtils
                    .formatDuration(expirationDuration.toMillis(), "H'h' m'm' s's'"));
        } catch (DateTimeParseException | NullPointerException exception) {
            LOGGER.error("Invalid or missing token expiration duration environment variable",
                    exception);
            throw exception;
        }
    }
}
