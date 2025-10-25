package ca.ulaval.glo4003.trotti.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.domain.account.services.PasswordHasher;
import ca.ulaval.glo4003.trotti.domain.authentication.services.AuthenticationService;
import ca.ulaval.glo4003.trotti.domain.commons.EmployeeRegistry;
import ca.ulaval.glo4003.trotti.domain.commons.communication.services.EmailService;
import ca.ulaval.glo4003.trotti.domain.commons.payment.security.DataCodec;
import ca.ulaval.glo4003.trotti.infrastructure.account.services.Argon2PasswordHasherAdapter;
import ca.ulaval.glo4003.trotti.infrastructure.authentication.JwtAuthenticationServiceAdapter;
import ca.ulaval.glo4003.trotti.infrastructure.commons.communication.services.JakartaEmailServiceAdapter;
import ca.ulaval.glo4003.trotti.infrastructure.commons.payment.security.AesDataCodecAdapter;
import ca.ulaval.glo4003.trotti.infrastructure.config.JakartaMailServiceConfiguration;
import io.jsonwebtoken.Jwts;
import java.security.NoSuchAlgorithmException;
import java.time.Clock;
import java.time.Duration;
import java.time.format.DateTimeParseException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ForeignServiceLoader extends Bootstrapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(ForeignServiceLoader.class);
    private static final String EXPIRATION_DURATION = "TOKEN_EXPIRATION_DURATION";
    private static final Duration DEFAULT_TOKEN_EXPIRATION = Duration.ofMinutes(60);
    private static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();

    private static final String EMAIL_SMTP_USER = "STMP_USER";
    private static final String EMAIL_SMTP_PASSWORD = "STMP_PASS";
    private static final String EMAIL_SMTP_HOST = "STMP_HOST";
    private static final String EMAIL_SMTP_PORT = "STMP_PORT";

    private static final int HASHER_MEMORY_COST = 65536;
    private static final int HASHER_ITERATIONS = 3;
    private static final int HASHER_NUMBER_OF_THREADS = 1;

    @Override
    public void load() {
        loadPasswordHasherService();
        loadEmailService();
        loadAuthenticationService();
        loadDataEncoder();
    }

    private void loadPasswordHasherService() {
        PasswordHasher hasher = new Argon2PasswordHasherAdapter(HASHER_MEMORY_COST,
                HASHER_ITERATIONS, HASHER_NUMBER_OF_THREADS);
        this.resourceLocator.register(PasswordHasher.class, hasher);
    }

    private void loadEmailService() {
        String username = System.getenv(EMAIL_SMTP_USER);
        String password = System.getenv(EMAIL_SMTP_PASSWORD);
        String host = System.getenv(EMAIL_SMTP_HOST);
        String port = System.getenv(EMAIL_SMTP_PORT);

        JakartaMailServiceConfiguration emailConfiguration =
                JakartaMailServiceConfiguration.create(username, password, host, port);
        EmailService emailService = new JakartaEmailServiceAdapter(emailConfiguration.connect());
        this.resourceLocator.register(EmailService.class, emailService);
    }

    private void loadAuthenticationService() {
        try {
            EmployeeRegistry employeeRegistry =
                    this.resourceLocator.resolve(EmployeeRegistry.class);

            String durationValue = StringUtils.isEmpty(System.getenv(EXPIRATION_DURATION))
                    ? DEFAULT_TOKEN_EXPIRATION.toString()
                    : System.getenv(EXPIRATION_DURATION);

            Duration expirationDuration = Duration.parse(durationValue);
            Clock authenticatorClock = this.resourceLocator.resolve(Clock.class);
            AuthenticationService authenticationService = new JwtAuthenticationServiceAdapter(
                    expirationDuration, authenticatorClock, SECRET_KEY, employeeRegistry);

            this.resourceLocator.register(AuthenticationService.class, authenticationService);
            LOGGER.info("Token expiration duration set to {}", DurationFormatUtils
                    .formatDuration(expirationDuration.toMillis(), "H'h' m'm' s's'"));
        } catch (DateTimeParseException | NullPointerException exception) {
            LOGGER.error("Invalid or missing token expiration duration environment variable",
                    exception);
            throw exception;
        }
    }

    private void loadDataEncoder() {
        this.resourceLocator.register(DataCodec.class,
                new AesDataCodecAdapter(generateSecretKey()));
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
