package ca.ulaval.glo4003.trotti.infrastructure.auth;

import ca.ulaval.glo4003.trotti.domain.account.Authenticator;
import ca.ulaval.glo4003.trotti.domain.account.Idul;
import ca.ulaval.glo4003.trotti.domain.shared.Id;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;


public class AuthenticatorAdapter implements Authenticator {
	
	private final Duration expirationDuration;
	private final Clock clock;
	private final SecretKey secretKey;
	
	
	public AuthenticatorAdapter(Duration expirationDuration, Clock clock, SecretKey secretKey) {
		this.expirationDuration = expirationDuration;
		this.clock = clock;
        this.secretKey = secretKey;
	}
	
	public AuthToken generateToken(Idul accountId) {
		Instant now = Instant.now(clock);
		
		String tokenValue = Jwts
				.builder()
				.id(Id.randomId().toString())
				.subject(accountId.toString())
				.issuedAt(Date.from(now))
				.expiration(Date.from(now.plus(expirationDuration)))
				.signWith(secretKey, Jwts.SIG.HS256)
				.compact();
		
		return AuthToken.from(tokenValue);
	}
	
	public Idul authenticate(AuthToken token) {
		String idulValue = Jwts
				.parser()
				.verifyWith(secretKey)
				.build()
				.parseSignedClaims(token.getValue())
				.getPayload()
				.getSubject();
		
		return Idul.from(idulValue);
	}
}