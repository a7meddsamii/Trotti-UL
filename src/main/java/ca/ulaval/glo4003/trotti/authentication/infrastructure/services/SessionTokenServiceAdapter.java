package ca.ulaval.glo4003.trotti.authentication.infrastructure.services;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.authentication.domain.exception.AuthenticationException;
import ca.ulaval.glo4003.trotti.authentication.domain.services.SessionTokenService;
import ca.ulaval.glo4003.trotti.authentication.domain.values.AuthenticatedIdentity;
import ca.ulaval.glo4003.trotti.authentication.domain.values.Permission;
import ca.ulaval.glo4003.trotti.authentication.domain.values.Role;
import ca.ulaval.glo4003.trotti.authentication.domain.values.SessionToken;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;

import javax.crypto.SecretKey;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Set;

public class SessionTokenServiceAdapter implements SessionTokenService {
	
	private final Duration expirationDuration;
	private final Clock clock;
	private final SecretKey secretKey;
	
	public SessionTokenServiceAdapter(
			Duration expirationDuration,
			Clock clock,
			SecretKey secretKey
	) {
		this.expirationDuration = expirationDuration;
		this.clock = clock;
		this.secretKey = secretKey;
	}
	
	@Override
	public SessionToken generateToken(Idul idul, Role role, Set<Permission> permissions) {
		Instant now = clock.instant();
		
		String tokenValue = Jwts.builder().subject(idul.toString()).issuedAt(Date.from(now))
				.expiration(Date.from(now.plus(expirationDuration)))
				.signWith(secretKey, Jwts.SIG.HS256).compact();
		
		return SessionToken.from(tokenValue);
	}
	
	@Override
	public AuthenticatedIdentity deserialize(SessionToken token) {
		try {
			String idulValue =
					Jwts.parser().verifyWith(secretKey).clock(() -> Date.from(clock.instant()))
							.build().parseSignedClaims(token.toString()).getPayload().getSubject();
			
			return new AuthenticatedIdentity(Idul.from(idulValue), Role.REGULAR_EMPLOYEE, Set.of(Permission.START_MAINTENANCE, Permission.END_MAINTENANCE));
		} catch (ExpiredJwtException exception) {
			throw new AuthenticationException("Session is already expired");
		} catch (MalformedJwtException exception) {
			throw new AuthenticationException("Session token is either malformed or invalid");
		} catch (JwtException exception) {
			throw new AuthenticationException("Something went wrong during the session authentication");
		}
	}
}
