package ca.ulaval.glo4003.trotti.authentication.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.account.infrastructure.config.loaders.AccountForeignServiceLoader;
import ca.ulaval.glo4003.trotti.authentication.domain.services.SessionTokenGenerator;
import ca.ulaval.glo4003.trotti.authentication.infrastructure.security.authentication.jwtsecuritycontext.JwtSessionTokenGeneratorAdapter;
import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;
import java.time.Clock;
import java.time.Duration;
import java.time.format.DateTimeParseException;

public class AuthenticationForeignServiceLoader extends Bootstrapper {
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationForeignServiceLoader.class);
	
	private static final String EXPIRATION_DURATION = "TOKEN_EXPIRATION_DURATION";
	private static final Duration DEFAULT_TOKEN_EXPIRATION = Duration.ofMinutes(60);
	private static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();
	
	@Override
	public void load() {
		this.loadSessionTokenGenerator();
	}
	
	private void loadSessionTokenGenerator() {
		try {
			String durationValue = StringUtils.defaultIfBlank(System.getenv(EXPIRATION_DURATION),DEFAULT_TOKEN_EXPIRATION.toString());
			Duration expirationDuration = Duration.parse(durationValue);
			Clock clock = this.resourceLocator.resolve(Clock.class);
			SessionTokenGenerator sessionTokenGenerator = new JwtSessionTokenGeneratorAdapter(expirationDuration, clock, SECRET_KEY);
			
			this.resourceLocator.register(SessionTokenGenerator.class, sessionTokenGenerator);
			LOGGER.info("Token expiration duration set to {}", DurationFormatUtils.formatDuration(expirationDuration.toMillis(), "H'h' m'm' s's'"));
		} catch (DateTimeParseException | NullPointerException exception) {
			LOGGER.error("Invalid or missing token expiration duration environment variable", exception);
			throw exception;
		}
	}
}