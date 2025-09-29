package ca.ulaval.glo4003.trotti.infrastructure.sessions.repository;

import ca.ulaval.glo4003.trotti.domain.order.Session;
import ca.ulaval.glo4003.trotti.domain.order.repository.SessionRepository;
import ca.ulaval.glo4003.trotti.domain.order.values.Semester;
import ca.ulaval.glo4003.trotti.infrastructure.sessions.mappers.SessionMapper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class JsonFileSessionRepositoryIntegrationTest {

    private static final Path RESOURCE_PATH =
            Path.of("src/main/resources/data/semesters-252627.json");
    private static final int EXPECTED_NUMBER_OF_SESSIONS = 6;

    private SessionRepository sessionRepository;

    @BeforeEach
    void setUp() {
        SessionMapper sessionMapper = new SessionMapper();
        sessionRepository = new JsonFileSessionRepository(sessionMapper, RESOURCE_PATH);
    }

    @Test
    void givenValidResourcePath_whenFindAll_thenReturnAllSession() {
        List<Session> sessions = sessionRepository.findAll();

        Assertions.assertNotNull(sessions);
        Assertions.assertEquals(EXPECTED_NUMBER_OF_SESSIONS, sessions.size());
    }

    @Test
    void givenValidResourcePath_whenFindAll_thenReturnCorrectSessions() {
        List<Session> sessions = sessionRepository.findAll();

        Assertions.assertEquals(Semester.FALL, sessions.get(0).getSemester());
        Assertions.assertEquals(Semester.WINTER, sessions.get(1).getSemester());
        Assertions.assertEquals(Semester.SUMMER, sessions.get(2).getSemester());
        Assertions.assertEquals(Semester.FALL, sessions.get(3).getSemester());
        Assertions.assertEquals(Semester.WINTER, sessions.get(4).getSemester());
        Assertions.assertEquals(Semester.SUMMER, sessions.get(5).getSemester());
    }

    @Test
    void givenEmptyJsonArray_whenFindAll_thenReturnEmptyList() throws IOException {
        Path tempFile = Files.createTempFile("empty", ".json");
        Files.writeString(tempFile, "[]");
        SessionRepository emptyRepository = new JsonFileSessionRepository(new SessionMapper(), tempFile);

        List<Session> sessions = emptyRepository.findAll();
        
        Assertions.assertTrue(sessions.isEmpty());
    }

    @Test
    void givenInvalidJson_whenFindAll_thenThrowRuntimeException() throws IOException {
        Path tempFile = Files.createTempFile("invalid", ".json");
        Files.writeString(tempFile, "{ invalid json }");
        SessionRepository repository =
                new JsonFileSessionRepository(new SessionMapper(), tempFile);

        Executable invalidFindAll = repository::findAll;

        Assertions.assertThrows(RuntimeException.class, invalidFindAll);
    }

}
