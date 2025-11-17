package ca.ulaval.glo4003.trotti.account.infrastructure.services;

import ca.ulaval.glo4003.trotti.account.domain.exceptions.AuthenticationException;
import ca.ulaval.glo4003.trotti.account.domain.exceptions.ExpiredTokenException;
import ca.ulaval.glo4003.trotti.account.domain.exceptions.MalformedTokenException;
import ca.ulaval.glo4003.trotti.account.domain.services.AuthenticationService;
import ca.ulaval.glo4003.trotti.account.domain.values.AuthenticationToken;
import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.account.domain.provider.EmployeeRegistryProvider;
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
    private final EmployeeRegistryProvider employeeRegistryProvider;

    public JwtAuthenticationServiceAdapter(
            Duration expirationDuration,
            Clock clock,
            SecretKey secretKey,
            EmployeeRegistryProvider employeeRegistryProvider
	) {
        this.expirationDuration = expirationDuration;
        this.clock = clock;
        this.secretKey = secretKey;
        this.employeeRegistryProvider = employeeRegistryProvider;
    }

    @Override
    public AuthenticationToken generateToken(Idul idul) {
        Instant now = clock.instant();

        String tokenValue = Jwts.builder().subject(idul.toString()).issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(expirationDuration)))
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
        if (employeeRegistryProvider.exist(idul)) {
            throw new AuthenticationException(
                    "Employees are not allowed to complete this operation");
        }
    }
}
