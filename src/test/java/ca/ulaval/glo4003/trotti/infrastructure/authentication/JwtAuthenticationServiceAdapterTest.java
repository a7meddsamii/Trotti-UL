package ca.ulaval.glo4003.trotti.infrastructure.authentication;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.authentication.AuthenticationToken;
import ca.ulaval.glo4003.trotti.domain.authentication.exceptions.AuthenticationException;
import ca.ulaval.glo4003.trotti.domain.authentication.exceptions.ExpiredTokenException;
import ca.ulaval.glo4003.trotti.domain.authentication.exceptions.MalformedTokenException;
import ca.ulaval.glo4003.trotti.domain.commons.EmployeeRegistry;
import io.jsonwebtoken.JwtException;
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

class JwtAuthenticationServiceAdapterTest {
    private static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();
    private static final Duration AN_EXPIRATION_DURATION = Duration.ofMinutes(60);
    private static final Idul AN_IDUL = Idul.from("anIdul");
    private static final AuthenticationToken MALFORMED_TOKEN =
            AuthenticationToken.from("malformedToken");
    private static final Instant START_MOMENT = Instant.parse("2025-09-19T10:00:00Z");
    private static final Instant FUTURE_TIME_WITHIN_EXPIRATION_DURATION =
            START_MOMENT.plus(AN_EXPIRATION_DURATION).minusSeconds(4);
    private static final Instant FUTURE_TIME_OVER_EXPIRATION_DURATION =
            START_MOMENT.plus(AN_EXPIRATION_DURATION).plusSeconds(4);
    private static final ZoneOffset UTC = ZoneOffset.UTC;

    private JwtAuthenticationServiceAdapter jwtAuthenticatorAdapter;
    private Clock clock;
    private EmployeeRegistry employeeRegistry;

    @BeforeEach
    void setup() {
        employeeRegistry = Mockito.mock(EmployeeRegistry.class);
        clock = Mockito.spy(Clock.fixed(START_MOMENT, UTC));
        jwtAuthenticatorAdapter = new JwtAuthenticationServiceAdapter(AN_EXPIRATION_DURATION, clock,
                SECRET_KEY, employeeRegistry);
    }

    @Test
    void givenIdul_whenGenerateToken_thenReturnAuthToken() {
        AuthenticationToken token = jwtAuthenticatorAdapter.generateToken(AN_IDUL);

        Assertions.assertNotNull(token);
    }

    @Test
    void givenAuthTokenNotExpired_whenAuthenticate_thenReturnSameIdulUsedToGenerateToken() {
        AuthenticationToken token = jwtAuthenticatorAdapter.generateToken(AN_IDUL);
        Mockito.when(clock.instant()).thenReturn(START_MOMENT);
        Mockito.when(clock.instant()).thenReturn(FUTURE_TIME_WITHIN_EXPIRATION_DURATION);

        Idul idul = jwtAuthenticatorAdapter.authenticate(token);

        Assertions.assertEquals(AN_IDUL, idul);
    }

    @Test
    void givenAuthTokenExpired_whenAuthenticate_thenThrowsException() {
        AuthenticationToken token = jwtAuthenticatorAdapter.generateToken(AN_IDUL);
        Mockito.when(clock.instant()).thenReturn(FUTURE_TIME_OVER_EXPIRATION_DURATION);

        Executable authenticationAction = () -> jwtAuthenticatorAdapter.authenticate(token);

        Assertions.assertThrows(ExpiredTokenException.class, authenticationAction);
    }

    @Test
    void givenMalformedToken_whenAuthenticate_thenThrowsException() {
        Executable authenticationAction =
                () -> jwtAuthenticatorAdapter.authenticate(MALFORMED_TOKEN);

        Assertions.assertThrows(MalformedTokenException.class, authenticationAction);
    }

    @Test
    void givenAnyValidAuthTokenAndUnknownMalfunction_whenAuthenticate_thenThrowsGeneralException() {
        AuthenticationToken anyToken = jwtAuthenticatorAdapter.generateToken(AN_IDUL);
        Mockito.when(clock.instant()).thenThrow(JwtException.class);

        Executable authenticationAction = () -> jwtAuthenticatorAdapter.authenticate(anyToken);

        Assertions.assertThrows(AuthenticationException.class, authenticationAction);
    }

    @Test
    void givenEmployeeIdul_whenConfirmStudent_thenExceptionIsThrown() {
        Mockito.when(employeeRegistry.isEmployee(AN_IDUL)).thenReturn(true);

        Executable confirmStudentAction = () -> jwtAuthenticatorAdapter.confirmStudent(AN_IDUL);

        Assertions.assertThrows(AuthenticationException.class, confirmStudentAction);
    }

    @Test
    void givenStudentIdul_whenConfirmStudent_thenNoExceptionThrown() {
        Mockito.when(employeeRegistry.isEmployee(AN_IDUL)).thenReturn(false);

        Executable confirmStudentAction = () -> jwtAuthenticatorAdapter.confirmStudent(AN_IDUL);

        Assertions.assertDoesNotThrow(confirmStudentAction);
    }
}
