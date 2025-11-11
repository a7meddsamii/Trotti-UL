package ca.ulaval.glo4003.trotti.authentication.infrastructure.security.authentication.jwtsecuritycontext;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.authentication.domain.exception.AuthenticationException;
import ca.ulaval.glo4003.trotti.authentication.domain.services.SessionTokenGenerator;
import ca.ulaval.glo4003.trotti.authentication.domain.values.AuthenticatedIdentity;
import ca.ulaval.glo4003.trotti.authentication.domain.values.Permission;
import ca.ulaval.glo4003.trotti.authentication.domain.values.Role;
import ca.ulaval.glo4003.trotti.authentication.domain.values.SessionToken;
import io.jsonwebtoken.*;

import javax.crypto.SecretKey;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class JwtSessionTokenGeneratorAdapter implements SessionTokenGenerator {
	
	private final Duration expirationDuration;
	private final Clock clock;
	private final SecretKey secretKey;
	
	public JwtSessionTokenGeneratorAdapter(
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
		
		String tokenValue = Jwts.builder().subject(idul.toString())
				.claim("role", role.toString())
				.claim("permissions", permissions.stream().map(Enum::toString).toList())
				.issuedAt(Date.from(now))
				.expiration(Date.from(now.plus(expirationDuration)))
				.signWith(secretKey, Jwts.SIG.HS256).compact();
		
		return SessionToken.from(tokenValue);
	}
	
	@Override
	public AuthenticatedIdentity deserialize(SessionToken token) {
		try {
			Claims claims = Jwts.parser().verifyWith(secretKey).clock(() -> Date.from(clock.instant()))
					.build().parseSignedClaims(token.toString()).getPayload();
			
			Idul idul = Idul.from(claims.getSubject());
			Role role = Role.fromString(claims.get("role", String.class));
			List<?> permsRaw = claims.get("permissions", List.class);
			Set<Permission> permissions = permsRaw.stream()
					.filter(String.class::isInstance)
					.map(String.class::cast)
					.map(Permission::valueOf)
					.collect(Collectors.toSet());
			
			
			return new AuthenticatedIdentity(idul, role, permissions);
		} catch (ExpiredJwtException exception) {
			throw new AuthenticationException("Session is already expired");
		} catch (MalformedJwtException exception) {
			throw new AuthenticationException("Session token is either malformed or invalid");
		} catch (JwtException exception) {
			throw new AuthenticationException("Something went wrong during the session authentication");
		}
	}
}
