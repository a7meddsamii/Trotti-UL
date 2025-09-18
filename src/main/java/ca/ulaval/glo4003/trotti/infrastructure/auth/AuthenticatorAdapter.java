package ca.ulaval.glo4003.trotti.infrastructure.auth;

import ca.ulaval.glo4003.trotti.domain.account.Authenticator;
import ca.ulaval.glo4003.trotti.domain.account.Idul;
import ca.ulaval.glo4003.trotti.domain.shared.Id;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;


public class AuthenticatorAdapter implements Authenticator {
	
	private static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();
	private final Duration expirationDuration;
	private final Clock clock;
	
	public AuthenticatorAdapter(Duration expirationDuration, Clock clock) {
		this.expirationDuration = expirationDuration;
		this.clock = clock;
	}
	
	public String generateToken(Idul accountId) {
		Instant now = Instant.now(clock);
		
		return Jwts
				.builder()
				.id(Id.randomId().toString())
				.subject(accountId.toString())
				.issuedAt(Date.from(now))
				.expiration(Date.from(now.plus(expirationDuration)))
				.signWith(SECRET_KEY, Jwts.SIG.HS256)
				.compact();
	}
	
	public Idul authenticate(String token) {
		/* TODO authentication process
			- lancer une exception si le token est invalide
			- lancer une exception si la période est expiré
			- extrait le subject (idul) du token et le retourner si tout est ok
		 */
				
		return null;
	}
}
