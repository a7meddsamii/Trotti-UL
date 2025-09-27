package ca.ulaval.glo4003.trotti.infrastructure.config.binders;

import ca.ulaval.glo4003.trotti.domain.account.authentication.AuthenticationService;
import ca.ulaval.glo4003.trotti.domain.commons.EmailService;
import ca.ulaval.glo4003.trotti.infrastructure.authentication.JwtAuthenticationServiceAdapter;
import ca.ulaval.glo4003.trotti.infrastructure.config.JakartaMailServiceConfiguration;
import ca.ulaval.glo4003.trotti.infrastructure.config.ServerResourceLocator;
import ca.ulaval.glo4003.trotti.infrastructure.email.JakartaEmailService;
import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Jwts;
import java.time.Clock;
import java.time.Duration;
import java.time.format.DateTimeParseException;
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

    private static ServerResourceInstantiation instance;
    private final ServerResourceLocator locator;
    private boolean resourcesCreated;
    private final Dotenv dotenv;

    public static ServerResourceInstantiation getInstance() {
        if (instance != null) {
            return instance;
        }

        return instance = new ServerResourceInstantiation();
    }

    private ServerResourceInstantiation() {
        this.locator = ServerResourceLocator.getInstance();
        this.resourcesCreated = false;
        this.dotenv = Dotenv.load();
    }

    private void loadAuthenticatorResource() {
        try {
            String durationValue = StringUtils.isEmpty(dotenv.get(EXPIRATION_DURATION))
                    ? DEFAULT_TOKEN_EXPIRATION.toString()
                    : dotenv.get(EXPIRATION_DURATION);
            Duration expirationDuration = Duration.parse(durationValue);
            Clock authenticatorClock = Clock.systemDefaultZone();
            AuthenticationService authenticator = new JwtAuthenticationServiceAdapter(
                    expirationDuration, authenticatorClock, SECRET_KEY);
            locator.register(AuthenticationService.class, authenticator);
            LOGGER.info("Token expiration duration set to {}", DurationFormatUtils
                    .formatDuration(expirationDuration.toMillis(), "H'h' m'm' s's'"));
        } catch (DateTimeParseException | NullPointerException exception) {
            LOGGER.error("Invalid or missing token expiration duration environment variable",
                    exception);
            throw exception;
        }
    }

    private void loadEmailSender() {
        String username = dotenv.get(EMAIL_USER);
        String password = dotenv.get(EMAIL_PASSWORD);
        String host = dotenv.get(EMAIL_HOST);
        String port = dotenv.get(EMAIL_PORT);

        JakartaMailServiceConfiguration emailConfiguration =
                JakartaMailServiceConfiguration.create(username, password, host, port);
        EmailService emailService = new JakartaEmailService(emailConfiguration.connect());
        locator.register(EmailService.class, emailService);
    }

    public void initiate() {
        if (resourcesCreated) {
            return;
        }

        loadAuthenticatorResource();
        loadEmailSender();
        resourcesCreated = true;
    }
}
