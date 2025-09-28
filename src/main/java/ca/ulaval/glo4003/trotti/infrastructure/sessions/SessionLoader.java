package ca.ulaval.glo4003.trotti.infrastructure.sessions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class SessionLoader {
    private static final String SESSION_PATH = "/data/semesters-252627.json";

    private static final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    private static final SessionMapper sessionMapper = new SessionMapper();

    public static List<SessionDTO> loadSessions() {
        try (InputStream inputStream = SessionLoader.class.getResourceAsStream(SESSION_PATH)) {
            if (inputStream == null) {
                throw new FileNotFoundException(SESSION_PATH + " not found in classpath");
            }

            return  mapper.readValue(
                    inputStream,
                    mapper.getTypeFactory().constructCollectionType(List.class, SessionDTO.class));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load sessions from " + SESSION_PATH, e);
        }
    }
}
