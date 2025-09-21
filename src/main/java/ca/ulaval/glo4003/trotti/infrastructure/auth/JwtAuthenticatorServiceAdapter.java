package ca.ulaval.glo4003.trotti.infrastructure.auth;

import ca.ulaval.glo4003.trotti.domain.account.Idul;
import ca.ulaval.glo4003.trotti.domain.account.auth.AuthToken;
import ca.ulaval.glo4003.trotti.domain.account.auth.AuthenticatorService;
import ca.ulaval.glo4003.trotti.domain.commons.Id;
import io.jsonwebtoken.Jwts;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import javax.crypto.SecretKey;


public class JwtAuthenticatorServiceAdapter implements AuthenticatorService {
	
	private final Duration expirationDuration;
	private final Clock clock;
	private final SecretKey secretKey;
	
	
	public JwtAuthenticatorServiceAdapter(Duration expirationDuration, Clock clock, SecretKey secretKey) {
		this.expirationDuration = expirationDuration;
		this.clock = clock;
		this.secretKey = secretKey;
	}
	
	@Override
	public AuthToken generateToken(Idul idul) {
		Instant now = clock.instant();
		
		String tokenValue = Jwts.builder().id(Id.randomId().toString())
				.subject(idul.toString()).issuedAt(Date.from(now))
				.expiration(Date.from(now.plus(expirationDuration)))
				.signWith(secretKey, Jwts.SIG.HS256).compact();
		
		return AuthToken.from(tokenValue);
	}
	
	@Override
	public Idul authenticate(AuthToken token) {
		String idulValue =
				Jwts.parser().verifyWith(secretKey).clock(() -> Date.from(clock.instant())).build()
						.parseSignedClaims(token.toString()).getPayload().getSubject();
		
		return Idul.from(idulValue);
	}
}
