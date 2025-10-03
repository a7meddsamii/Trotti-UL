package ca.ulaval.glo4003.trotti.domain.commons;

import ca.ulaval.glo4003.trotti.domain.order.Session;
import ca.ulaval.glo4003.trotti.domain.order.values.Semester;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SessionRegistryTest {

    private static final List<Session> SESSIONS = List
            .of(new Session(Semester.FALL, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 6, 30)));
    private static final LocalDate DATE_IN_SESSION = LocalDate.of(2024, 3, 15);
    private static final LocalDate DATE_NOT_IN_SESSION = LocalDate.of(2024, 12, 15);

    private SessionRegistry sessionRegistry;

    @BeforeEach
    void setUp() {
        sessionRegistry = new SessionRegistry(SESSIONS);
    }

    @Test
    void givenDateInSession_whenGetSession_thenReturnsSession() {
        Optional<Session> session = sessionRegistry.getSession(DATE_IN_SESSION);

        Assertions.assertTrue(session.isPresent());
    }

    @Test
    void givenDateNotInSession_whenGetSession_thenReturnsEmpty() {
        Optional<Session> session = sessionRegistry.getSession(DATE_NOT_IN_SESSION);

        Assertions.assertFalse(session.isPresent());
    }
}
