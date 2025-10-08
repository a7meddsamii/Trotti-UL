package ca.ulaval.glo4003.trotti.infrastructure.authentication;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.authentication.exceptions.AuthenticationException;
import ca.ulaval.glo4003.trotti.domain.authentication.exceptions.ExpiredTokenException;
import ca.ulaval.glo4003.trotti.domain.authentication.exceptions.MalformedTokenException;
import ca.ulaval.glo4003.trotti.domain.authentication.services.AuthenticationService;
import ca.ulaval.glo4003.trotti.domain.authentication.values.AuthenticationToken;
import ca.ulaval.glo4003.trotti.domain.commons.EmployeeRegistry;
import ca.ulaval.glo4003.trotti.domain.commons.Id;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import javax.crypto.SecretKey;

public class JwtAuthenticationServiceAdapter implements AuthenticationService {

    private final Duration expirationDuration;
    private final Clock clock;
    private final SecretKey secretKey;
    private final EmployeeRegistry employeeRegistry;

    public JwtAuthenticationServiceAdapter(
            Duration expirationDuration,
            Clock clock,
            SecretKey secretKey,
            EmployeeRegistry employeeRegistry) {
        this.expirationDuration = expirationDuration;
        this.clock = clock;
        this.secretKey = secretKey;
        this.employeeRegistry = employeeRegistry;
    }

    @Override
    public AuthenticationToken generateToken(Idul idul) {
        Instant now = clock.instant();

        String tokenValue = Jwts.builder().id(Id.randomId().toString()).subject(idul.toString())
                .issuedAt(Date.from(now)).expiration(Date.from(now.plus(expirationDuration)))
                .signWith(secretKey, Jwts.SIG.HS256).compact();

        return AuthenticationToken.from(tokenValue);
    }

    @Override
    public Idul authenticate(AuthenticationToken token) {
        try {
            String idulValue =
                    Jwts.parser().verifyWith(secretKey).clock(() -> Date.from(clock.instant()))
                            .build().parseSignedClaims(token.toString()).getPayload().getSubject();

            return Idul.from(idulValue);
        } catch (ExpiredJwtException exception) {
            throw new ExpiredTokenException();
        } catch (MalformedJwtException exception) {
            throw new MalformedTokenException("The token received is either malformed or invalid");
        } catch (JwtException exception) {
            throw new AuthenticationException("Something went wrong during the authentication");
        }
    }

    @Override
    public void confirmStudent(Idul idul) {
        if (employeeRegistry.isEmployee(idul)) {
            throw new AuthenticationException(
                    "Employees are not allowed to complete this operation");
        }
    }
}
