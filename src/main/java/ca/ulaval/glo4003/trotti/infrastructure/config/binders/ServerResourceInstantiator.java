package ca.ulaval.glo4003.trotti.infrastructure.config.binders;

import ca.ulaval.glo4003.trotti.infrastructure.auth.AuthenticatorAdapter;
import ca.ulaval.glo4003.trotti.infrastructure.config.ServerResourceLocator;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;
import java.time.Clock;
import java.time.Duration;
import java.time.format.DateTimeParseException;

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
			AuthenticatorAdapter authenticator = new AuthenticatorAdapter(expirationDuration, authenticatorClock, SECRET_KEY);
			locator.register(AuthenticatorAdapter.class, authenticator);
		} catch (DateTimeParseException exception) {
			LOGGER.error("Invalid token expiration duration", exception);
			System.exit(-1);
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