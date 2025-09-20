package ca.ulaval.glo4003.trotti.infrastructure.config.binders;

import ca.ulaval.glo4003.trotti.domain.account.auth.Authenticator;
import ca.ulaval.glo4003.trotti.infrastructure.auth.JwtAuthenticatorAdapter;
import ca.ulaval.glo4003.trotti.infrastructure.config.ServerResourceLocator;
import io.jsonwebtoken.Jwts;
import java.time.Clock;
import java.time.Duration;
import java.time.format.DateTimeParseException;
import javax.crypto.SecretKey;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerResourceInstantiator {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerResourceInstantiator.class);
    private static final String EXPIRATION_DURATION = "TOKEN_EXPIRATION_DURATION";
    private static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();

    private static ServerResourceInstantiator instance;
    private final ServerResourceLocator locator;
    private boolean resourcesCreated;

    public static ServerResourceInstantiator getInstance() {
        if (instance != null) {
            return instance;
        }

        return instance = new ServerResourceInstantiator();
    }

    private ServerResourceInstantiator() {
        this.locator = ServerResourceLocator.getInstance();
        this.resourcesCreated = false;
    }

    private void loadAuthenticatorResource() {
        try {
            Duration expirationDuration = Duration.parse(System.getenv(EXPIRATION_DURATION));
            Clock authenticatorClock = Clock.systemDefaultZone();
            JwtAuthenticatorAdapter authenticator =
                    new JwtAuthenticatorAdapter(expirationDuration, authenticatorClock, SECRET_KEY);
            locator.register(Authenticator.class, authenticator);
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
