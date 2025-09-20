package ca.ulaval.glo4003.trotti.infrastructure.auth;

import ca.ulaval.glo4003.trotti.domain.account.Idul;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

import javax.crypto.SecretKey;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;


class AuthenticatorAdapterTest {
	private static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();
	private static final Duration AN_EXPIRATION_DURATION = Duration.ofMinutes(60);
	private static final Idul AN_IDUL = Idul.from("equi10");
	private static final Instant START_MOMENT = Instant.parse("2025-09-19T10:00:00Z");
	private static final Instant FUTURE_TIME_WITHIN_EXPIRATION_DURATION = START_MOMENT.plus(AN_EXPIRATION_DURATION).minusSeconds(4);
	private static final Instant FUTURE_TIME_OVER_EXPIRATION_DURATION = START_MOMENT.plus(AN_EXPIRATION_DURATION).plusSeconds(4);
	private static final ZoneOffset UTC = ZoneOffset.UTC;
	
	private AuthenticatorAdapter authenticatorAdapter;
	private Clock clock;
	
	@BeforeEach
	void setup() {
		clock = Mockito.spy(Clock.fixed(START_MOMENT, UTC));
		authenticatorAdapter = new AuthenticatorAdapter(AN_EXPIRATION_DURATION, clock, SECRET_KEY);
	}
	
	@Test
	void givenIdul_whenGenerateToken_thenReturnsAuthToken() {
		AuthToken token = authenticatorAdapter.generateToken(AN_IDUL);
		
		Assertions.assertNotNull(token);
	}
	
	@Test
	void givenAuthTokenNotExpired_whenAuthenticate_thenReturnsSameIdulUsedToGenerateToken() {
		AuthToken token = authenticatorAdapter.generateToken(AN_IDUL);
		Mockito.when(clock.instant()).thenReturn(START_MOMENT);
		Mockito.when(clock.instant()).thenReturn(FUTURE_TIME_WITHIN_EXPIRATION_DURATION);
		
		Idul idul = authenticatorAdapter.authenticate(token);
		
		Assertions.assertEquals(AN_IDUL, idul);
	}
	
	@Test
	void givenAuthTokenExpired_whenAuthenticate_thenThrowsException() {
		AuthToken token = authenticatorAdapter.generateToken(AN_IDUL);
		Mockito.when(clock.instant()).thenReturn(FUTURE_TIME_OVER_EXPIRATION_DURATION);
		
		Executable authenticationAction = () -> authenticatorAdapter.authenticate(token);
		
		Assertions.assertThrows(ExpiredJwtException.class, authenticationAction);
	}
	
	
	
	@Test
	void generateToken_containsExpectedStandardClaims() {
		Clock clock = Clock.systemUTC();
		Duration expiry = Duration.ofMinutes(10);
		SecretKey key = Jwts.SIG.HS256.key().build();
		
		AuthenticatorAdapter sut = new AuthenticatorAdapter(expiry, clock, key);
		Idul idul = Idul.from("equi10");
		
		AuthToken token = sut.generateToken(idul);
		
		var claims = Jwts.parser()
				.verifyWith(key)
				.build()
				.parseSignedClaims(token.getValue())
				.getPayload();
		
		Instant now = Instant.now();
		Assertions.assertEquals("equi10", claims.getSubject());
		Assertions.assertNotNull(claims.getId());
		
		Instant iat = claims.getIssuedAt().toInstant();
		Instant exp = claims.getExpiration().toInstant();
		
		Assertions.assertTrue(
				!iat.isAfter(now.plusSeconds(2)) && !iat.isBefore(now.minusSeconds(2)),
				"iat should be ~now"
		);
		
		// exp should be iat + expiry (±2s)
		Instant expectedExp = iat.plus(expiry);
		Assertions.assertTrue(
				!exp.isAfter(expectedExp.plusSeconds(2)) && !exp.isBefore(expectedExp.minusSeconds(2)),
				"exp should be iat + expiry (±2s)"
		);
	}
	
	@Test
	void authenticate_returnsOriginalIdul_whenTokenValid() {
		SecretKey key = Jwts.SIG.HS256.key().build();
		AuthenticatorAdapter sut = new AuthenticatorAdapter(Duration.ofMinutes(10), Clock.systemUTC(), key);
		Idul idul = Idul.from("alexandra01");
		
		AuthToken token = sut.generateToken(idul);
		Idul parsed = sut.authenticate(token);
		
		Assertions.assertEquals(idul, parsed);
	}
	
	@Test
	void authenticate_throwsExpiredJwtException_whenTokenExpired() throws Exception {
		SecretKey key = Jwts.SIG.HS256.key().build();
		Duration expiry = Duration.ofMillis(10);
		AuthenticatorAdapter sut = new AuthenticatorAdapter(expiry, Clock.systemUTC(), key);
		AuthToken token = sut.generateToken(Idul.from("expired-user"));
		
		Thread.sleep(30);
		
		Assertions.assertThrows(io.jsonwebtoken.ExpiredJwtException.class, () -> sut.authenticate(token));
	}
	
	@Test
	void authenticate_throws_onMalformedToken() {
		SecretKey key = Jwts.SIG.HS256.key().build();
		var sut = new AuthenticatorAdapter(Duration.ofMinutes(10), Clock.systemUTC(), key);
		var bad = AuthToken.from("this-is-not-a-jwt");
		
		Assertions.assertThrows(io.jsonwebtoken.JwtException.class, () -> sut.authenticate(bad));
	}
	
	@Test
	void authenticate_throws_onTamperedSignature() {
		SecretKey key = Jwts.SIG.HS256.key().build();
		var sut = new AuthenticatorAdapter(Duration.ofMinutes(10), Clock.systemUTC(), key);
		var tok = sut.generateToken(Idul.from("equi10"));
		
		String v = tok.getValue();
		char last = v.charAt(v.length() - 1);
		char flipped = last == 'a' ? 'b' : 'a';
		var tampered = AuthToken.from(v.substring(0, v.length() - 1) + flipped);
		
		Assertions.assertThrows(io.jsonwebtoken.JwtException.class, () -> sut.authenticate(tampered));
	}
	
	@Test
	void authToken_from_throwsOnNullOrBlank() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> AuthToken.from(null));
		Assertions.assertThrows(IllegalArgumentException.class, () -> AuthToken.from(""));
	}
}