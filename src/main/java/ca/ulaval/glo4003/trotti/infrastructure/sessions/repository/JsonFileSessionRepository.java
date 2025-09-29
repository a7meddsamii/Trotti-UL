package ca.ulaval.glo4003.trotti.infrastructure.sessions.repository;

import ca.ulaval.glo4003.trotti.domain.commons.exceptions.InvalidSessionException;
import ca.ulaval.glo4003.trotti.domain.order.Session;
import ca.ulaval.glo4003.trotti.domain.order.repository.SessionRepository;
import ca.ulaval.glo4003.trotti.infrastructure.sessions.mappers.SessionMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class JsonFileSessionRepository implements SessionRepository {

    private final ObjectMapper objectMapper;
    private final SessionMapper sessionMapper;
    private final Path path;

    public JsonFileSessionRepository(SessionMapper sessionMapper, Path path) {
        this.sessionMapper = sessionMapper;
        this.path = path;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public List<Session> findAll() {
        try (InputStream input = Files.newInputStream(path)) {
            List<SessionRecord> sessionRecords =
                    objectMapper.readValue(input, new TypeReference<>() {});
            return sessionRecords.stream().map(sessionMapper::toDomain).toList();
        } catch (Exception e) {
            throw new InvalidSessionException("Failed to read sessions file");
        }
    }
}
