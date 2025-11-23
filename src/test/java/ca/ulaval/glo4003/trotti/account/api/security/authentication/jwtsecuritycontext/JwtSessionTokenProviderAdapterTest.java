package ca.ulaval.glo4003.trotti.account.api.security.authentication.jwtsecuritycontext;

import ca.ulaval.glo4003.trotti.account.domain.exceptions.AuthenticationException;
import ca.ulaval.glo4003.trotti.account.domain.values.AuthenticatedIdentity;
import ca.ulaval.glo4003.trotti.account.domain.values.Permission;
import ca.ulaval.glo4003.trotti.account.domain.values.Role;
import ca.ulaval.glo4003.trotti.account.domain.values.SessionToken;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import io.jsonwebtoken.Jwts;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Set;
import javax.crypto.SecretKey;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class JwtSessionTokenProviderAdapterTest {

    private static final Duration EXPIRATION_DURATION = Duration.ofMinutes(30);
    private static final Idul IDUL = Idul.from("user123");
    private static final Role ROLE = Role.STUDENT;
    private static final Set<Permission> PERMISSIONS = Set.of(Permission.MAKE_TRIP);
    private static final Instant NOW = Instant.parse("2023-01-01T12:00:00Z");

    private SecretKey secretKey;
    private Clock clock;
    private JwtSessionTokenProviderAdapter jwtSessionTokenProviderAdapter;

    @BeforeEach
    void setup() {
        secretKey = Jwts.SIG.HS256.key().build();
        clock = Clock.fixed(NOW, ZoneId.of("UTC"));
        jwtSessionTokenProviderAdapter =
                new JwtSessionTokenProviderAdapter(EXPIRATION_DURATION, clock, secretKey);
    }

    @Test
    void givenIdentityInfo_whenGenerateToken_thenReturnToken() {
        SessionToken token = jwtSessionTokenProviderAdapter.generateToken(IDUL, ROLE, PERMISSIONS);

        Assertions.assertNotNull(token);
    }

    @Test
    void givenGeneratedToken_whenDeserialize_thenReturnCorrectIdentity() {
        SessionToken token = jwtSessionTokenProviderAdapter.generateToken(IDUL, ROLE, PERMISSIONS);

        AuthenticatedIdentity identity = jwtSessionTokenProviderAdapter.deserialize(token);

        Assertions.assertEquals(IDUL, identity.idul());
        Assertions.assertEquals(ROLE, identity.role());
        Assertions.assertEquals(PERMISSIONS, identity.permissions());
    }

    @Test
    void givenExpiredToken_whenDeserialize_thenThrowAuthenticationException() {
        SessionToken token = jwtSessionTokenProviderAdapter.generateToken(IDUL, ROLE, PERMISSIONS);
        Clock futureClock =
                Clock.fixed(NOW.plus(EXPIRATION_DURATION).plusSeconds(1), ZoneId.of("UTC"));
        JwtSessionTokenProviderAdapter futureAdapter =
                new JwtSessionTokenProviderAdapter(EXPIRATION_DURATION, futureClock, secretKey);

        Executable action = () -> futureAdapter.deserialize(token);

        Assertions.assertThrows(AuthenticationException.class, action);
    }

    @Test
    void givenMalformedToken_whenDeserialize_thenThrowAuthenticationException() {
        SessionToken malformedToken = SessionToken.from("invalid.token.value");

        Executable action = () -> jwtSessionTokenProviderAdapter.deserialize(malformedToken);

        Assertions.assertThrows(AuthenticationException.class, action);
    }
}
