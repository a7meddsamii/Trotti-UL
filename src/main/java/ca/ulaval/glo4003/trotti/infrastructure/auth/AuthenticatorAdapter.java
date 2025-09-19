package ca.ulaval.glo4003.trotti.infrastructure.auth;

import ca.ulaval.glo4003.trotti.domain.account.Authenticator;
import ca.ulaval.glo4003.trotti.domain.account.Idul;
import ca.ulaval.glo4003.trotti.domain.shared.Id;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

import javax.crypto.KeyGenerator;
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
	
	public AuthToken generateToken(Idul accountId) {
		Instant now = Instant.now(clock);
		
		String tokenValue = Jwts
				.builder()
				.id(Id.randomId().toString())
				.subject(accountId.toString())
				.issuedAt(Date.from(now))
				.expiration(Date.from(now.plus(expirationDuration)))
				.signWith(SECRET_KEY, Jwts.SIG.HS256)
				.compact();
		
		return AuthToken.from(tokenValue);
	}
	
	public Idul authenticate(AuthToken token) {
		String idulValue = Jwts
				.parser()
				.verifyWith(SECRET_KEY)
				.build()
				.parseSignedClaims(token.getValue())
				.getPayload()
				.getSubject();
		
		return Idul.from(idulValue);
	}
}
