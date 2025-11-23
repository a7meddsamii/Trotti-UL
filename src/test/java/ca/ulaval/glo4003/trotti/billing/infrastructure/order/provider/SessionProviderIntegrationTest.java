package ca.ulaval.glo4003.trotti.billing.infrastructure.order.provider;

import ca.ulaval.glo4003.trotti.billing.domain.order.exceptions.SessionException;
import ca.ulaval.glo4003.trotti.billing.domain.order.provider.SchoolSessionProvider;
import ca.ulaval.glo4003.trotti.billing.domain.order.values.Session;
import ca.ulaval.glo4003.trotti.billing.infrastructure.order.session.JsonSchoolSessionProvider;
import ca.ulaval.glo4003.trotti.billing.infrastructure.order.session.SessionMapper;
import ca.ulaval.glo4003.trotti.config.json.CustomJsonProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.api.io.TempDir;

class SessionProviderIntegrationTest {
    private static final LocalDate DATE_INSIDE_SESSION = LocalDate.of(2025, 10, 15);
    private static final LocalDate DATE_OUTSIDE_ALL_AVAILABLE_SESSIONS = LocalDate.of(2023, 10, 16);
    private static final LocalDate A_DATE = LocalDate.of(2025, 10, 15);

    @TempDir
    private Path testingResourcePath;
    private Path temporaryFile;

    private SchoolSessionProvider sessionProvider;

    @BeforeEach
    void setup() {
        temporaryFile = testingResourcePath.resolve("sessionsTest.json");
        ObjectMapper mapper = CustomJsonProvider.getMapper();
        SessionMapper sessionMapper = new SessionMapper();
        sessionProvider = new JsonSchoolSessionProvider(temporaryFile, sessionMapper, mapper);
    }

    @Test
    void givenDateWithinAvailableSessions_whenGettingSession_thenReturnsExpectedSession()
            throws IOException {
        Files.writeString(temporaryFile, JsonSessionTestCaseData.VALID_SESSIONS_JSON);

        Optional<Session> session = sessionProvider.getSession(DATE_INSIDE_SESSION);

        Assertions.assertTrue(session.isPresent());
        Assertions.assertTrue(session.get().contains(DATE_INSIDE_SESSION));
    }

    @Test
    void givenDate_whenGettingPreviousSession_thenReturnsExpectedSession() throws IOException {
        Files.writeString(temporaryFile, JsonSessionTestCaseData.VALID_SESSIONS_JSON);
        LocalDate localDate = LocalDate.of(2026, 1, 10);
        String expectedSemester = "A25";

        Optional<Session> session = sessionProvider.getPreviousSession(localDate);

        Assertions.assertTrue(session.isPresent());
        Assertions.assertEquals(expectedSemester, session.get().toString());
    }

    @Test
    void givenDate_whenGettingPreviousSession_thenReturnsEmpty() throws IOException {
        Files.writeString(temporaryFile, JsonSessionTestCaseData.VALID_SESSIONS_JSON);
        LocalDate localDate = LocalDate.of(2025, 9, 8);

        Optional<Session> session = sessionProvider.getPreviousSession(localDate);

        Assertions.assertTrue(session.isEmpty());
    }

    @Test
    void givenDateOutsideAvailableSessions_whenGettingSession_thenReturnsEmpty()
            throws IOException {
        Files.writeString(temporaryFile, JsonSessionTestCaseData.VALID_SESSIONS_JSON);

        Optional<Session> session = sessionProvider.getSession(DATE_OUTSIDE_ALL_AVAILABLE_SESSIONS);

        Assertions.assertTrue(session.isEmpty());
    }

    @Test
    void givenMalformedSessionsJson_whenGettingSession_thenThrowsException() throws IOException {
        Files.writeString(temporaryFile, JsonSessionTestCaseData.MISSING_SEMESTER_CODE_JSON);

        Executable getSession = () -> sessionProvider.getSession(A_DATE);

        Assertions.assertThrows(SessionException.class, getSession);
    }
}
