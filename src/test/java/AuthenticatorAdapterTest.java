import ca.ulaval.glo4003.trotti.domain.account.Idul;
import ca.ulaval.glo4003.trotti.infrastructure.auth.AuthToken;
import ca.ulaval.glo4003.trotti.infrastructure.auth.AuthenticatorAdapter;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticatorAdapterTest {
    
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
        assertEquals("equi10", claims.getSubject());
        assertNotNull(claims.getId());
        
        Instant iat = claims.getIssuedAt().toInstant();
        Instant exp = claims.getExpiration().toInstant();
        
        assertTrue(!iat.isAfter(now.plusSeconds(2)) && !iat.isBefore(now.minusSeconds(2)),
                "iat should be ~now");
        
        // exp should be iat + expiry (±2s)
        Instant expectedExp = iat.plus(expiry);
        assertTrue(!exp.isAfter(expectedExp.plusSeconds(2)) && !exp.isBefore(expectedExp.minusSeconds(2)),
                "exp should be iat + expiry (±2s)");
    }
    
    @Test
    void authenticate_returnsOriginalIdul_whenTokenValid() {
        SecretKey key = Jwts.SIG.HS256.key().build();
        AuthenticatorAdapter sut = new AuthenticatorAdapter(Duration.ofMinutes(10), Clock.systemUTC(), key);
        Idul idul = Idul.from("alexandra01");
        
        AuthToken token = sut.generateToken(idul);
        Idul parsed = sut.authenticate(token);
        
        assertEquals(idul, parsed);
    }
    
    @Test
    void authenticate_throwsExpiredJwtException_whenTokenExpired() throws Exception {
        SecretKey key = Jwts.SIG.HS256.key().build();
        Duration expiry = Duration.ofMillis(10);
        AuthenticatorAdapter sut = new AuthenticatorAdapter(expiry, Clock.systemUTC(), key);
        AuthToken token = sut.generateToken(Idul.from("expired-user"));
        
        Thread.sleep(30);
        
        assertThrows(io.jsonwebtoken.ExpiredJwtException.class, () -> sut.authenticate(token));
    }
    
    @Test
    void authenticate_throws_onMalformedToken() {
        SecretKey key = Jwts.SIG.HS256.key().build();
        var sut = new AuthenticatorAdapter(Duration.ofMinutes(10), Clock.systemUTC(), key);
        var bad = AuthToken.from("this-is-not-a-jwt");
        
        assertThrows(io.jsonwebtoken.JwtException.class, () -> sut.authenticate(bad));
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
        
        assertThrows(io.jsonwebtoken.JwtException.class, () -> sut.authenticate(tampered));
    }
    
    @Test
    void authToken_from_throwsOnNullOrBlank() {
        assertThrows(IllegalArgumentException.class, () -> AuthToken.from(null));
        assertThrows(IllegalArgumentException.class, () -> AuthToken.from(""));
    }
}