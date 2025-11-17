package ca.ulaval.glo4003.trotti.order.infrastructure.provider.sessions;

import ca.ulaval.glo4003.trotti.config.json.CustomJsonProvider;
import ca.ulaval.glo4003.trotti.order.domain.values.Session;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @deprecated this will be deleted soon
 * switch to the new provider {@link ca.ulaval.glo4003.trotti.order.infrastructure.provider.sessions.JsonSessionProvider}
 */
public final class SessionProvider {

    private static List<Session> sessions;
    private static SessionProvider instance;

    private SessionProvider(Path path, SessionMapper sessionMapper) {
        ObjectMapper objectMapper = CustomJsonProvider.getMapper();
        try (InputStream input = Files.newInputStream(path)) {
            List<SessionRecord> sessionRecords =
                    objectMapper.readValue(input, new TypeReference<>() {});

            sessionRecords.sort(Comparator.comparing(SessionRecord::startDate));

            List<Session> mappedSessions =
                    sessionRecords.stream().map(sessionMapper::toDomain).toList();

            List<Session> adjustedSessions = new ArrayList<>();

            for (int i = 0; i < mappedSessions.size(); i++) {
                Session current = mappedSessions.get(i);
                LocalDate start = current.getStartDate();
                LocalDate end;

                if (i < mappedSessions.size() - 1) {
                    LocalDate nextStart = mappedSessions.get(i + 1).getStartDate();
                    end = nextStart.minusDays(1);
                } else {
                    end = current.getEndDate();
                }

                adjustedSessions.add(new Session(current.getSemester(), start, end));
            }

            sessions = List.copyOf(adjustedSessions);
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
