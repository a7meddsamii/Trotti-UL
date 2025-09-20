package ca.ulaval.glo4003.trotti.infrastructure.auth;

import ca.ulaval.glo4003.trotti.domain.account.Idul;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import javax.crypto.SecretKey;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;


class AuthenticatorAdapterTest {
    private static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();
    private static final Duration AN_EXPIRATION_DURATION = Duration.ofMinutes(60);
    private static final Idul AN_IDUL = Idul.from("equi10");
    private static final Instant START_MOMENT = Instant.parse("2025-09-19T10:00:00Z");
    private static final Instant FUTURE_TIME_WITHIN_EXPIRATION_DURATION =
            START_MOMENT.plus(AN_EXPIRATION_DURATION).minusSeconds(4);
    private static final Instant FUTURE_TIME_OVER_EXPIRATION_DURATION =
            START_MOMENT.plus(AN_EXPIRATION_DURATION).plusSeconds(4);
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
}
