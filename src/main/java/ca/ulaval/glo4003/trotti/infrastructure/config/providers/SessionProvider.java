package ca.ulaval.glo4003.trotti.infrastructure.config.providers;

import ca.ulaval.glo4003.trotti.domain.order.Session;
import ca.ulaval.glo4003.trotti.infrastructure.sessions.SessionRecord;
import ca.ulaval.glo4003.trotti.infrastructure.sessions.mappers.SessionMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public final class SessionProvider {

    private static List<Session> sessions;
    private static SessionProvider instance;

    private SessionProvider(Path path, SessionMapper sessionMapper) {
        ObjectMapper objectMapper = CustomJsonProvider.getMapper();
        try (InputStream input = Files.newInputStream(path)) {
            List<SessionRecord> sessionRecords =
                    objectMapper.readValue(input, new TypeReference<>() {});
            this.sessions = sessionRecords.stream().map(sessionMapper::toDomain).toList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to load sessions file at startup", e);
        }
    }

    public static void initialize(Path path, SessionMapper sessionMapper) {
        instance = new SessionProvider(path, sessionMapper);
    }

    public static SessionProvider getInstance() {
        if (instance == null) {
            throw new IllegalStateException(
                    "SessionProvider not initialized. Call initialize() first.");
        }
        return instance;
    }

    public List<Session> getSessions() {
        return sessions;
    }
}
