package ca.ulaval.glo4003.trotti.infrastructure.config.binders;

import ca.ulaval.glo4003.trotti.domain.account.Idul;
import ca.ulaval.glo4003.trotti.domain.account.authentication.AuthenticationService;
import ca.ulaval.glo4003.trotti.domain.account.repository.AccountRepository;
import ca.ulaval.glo4003.trotti.infrastructure.authentication.JwtAuthenticationServiceAdapter;
import ca.ulaval.glo4003.trotti.infrastructure.config.ServerResourceLocator;
import ca.ulaval.glo4003.trotti.infrastructure.repository.UserInMemoryDatabase;
import ca.ulaval.glo4003.trotti.infrastructure.repository.account.AccountEntity;
import ca.ulaval.glo4003.trotti.infrastructure.repository.account.InMemoryAccountRepository;
import ca.ulaval.glo4003.trotti.infrastructure.repository.account.PersistenceAccountMapper;
import ca.ulaval.glo4003.trotti.infrastructure.repository.order.BuyerEntity;
import io.jsonwebtoken.Jwts;

import java.time.Clock;
import java.time.Duration;
import java.time.format.DateTimeParseException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.crypto.SecretKey;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerResourceInstantiation {
	private static final Logger LOGGER = LoggerFactory.getLogger(ServerResourceInstantiation.class);
	private static final String EXPIRATION_DURATION = "TOKEN_EXPIRATION_DURATION";
	private static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();
	
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
	
	private void loadAuthenticationService() {
		try {
			Duration expirationDuration = Duration.parse(System.getenv(EXPIRATION_DURATION));
			Clock authenticatorClock = Clock.systemDefaultZone();
			AuthenticationService authenticator = new JwtAuthenticationServiceAdapter(
					expirationDuration, authenticatorClock, SECRET_KEY);
			locator.register(AuthenticationService.class, authenticator);
			LOGGER.info(
					"Token expiration duration set to {}", DurationFormatUtils
							.formatDuration(expirationDuration.toMillis(), "H'h' m'm' s's'")
			);
		} catch (DateTimeParseException | NullPointerException exception) {
			LOGGER.error(
					"Invalid or missing token expiration duration environment variable",
					exception
			);
			throw exception;
		}
	}
	
	private void loadAccountRepository() {
		ConcurrentMap<Idul, AccountEntity> accountTable = new ConcurrentHashMap<>();
		ConcurrentMap<Idul, BuyerEntity> buyerTable = new ConcurrentHashMap<>();
		UserInMemoryDatabase userInMemoryDatabase =
				new UserInMemoryDatabase(accountTable, buyerTable);
		PersistenceAccountMapper accountMapper = new PersistenceAccountMapper();
		AccountRepository accountRepository = new InMemoryAccountRepository(userInMemoryDatabase, accountMapper);
		locator.register(AccountRepository.class, accountRepository);
	}
	
	public void initiate() {
		if (resourcesCreated) {
			return;
		}
		
		loadAuthenticationService();
		loadAccountRepository();
		resourcesCreated = true;
	}
}
