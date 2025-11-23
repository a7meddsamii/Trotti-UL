package ca.ulaval.glo4003.trotti.billing.infrastructure.order.session;

import ca.ulaval.glo4003.trotti.billing.domain.order.exceptions.SessionException;
import ca.ulaval.glo4003.trotti.billing.domain.order.provider.SchoolSessionProvider;
import ca.ulaval.glo4003.trotti.billing.domain.order.values.Session;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class JsonSchoolSessionProvider implements SchoolSessionProvider {
    private final Path resourcePath;
    private final SessionMapper sessionMapper;
    private final ObjectMapper jsonMapper;

    public JsonSchoolSessionProvider(
            Path resourcePath,
            SessionMapper sessionMapper,
            ObjectMapper jsonProvider) {
        this.resourcePath = resourcePath;
        this.sessionMapper = sessionMapper;
        this.jsonMapper = jsonProvider;
    }
	
	@Override
	public Optional<Session> getPreviousSession(LocalDate date) {
		Set<Session> sessions =
				readSessions().stream().map(sessionMapper::toDomain).collect(Collectors.toSet());
		
		List<Session> sortedSessions = sessions.stream()
				.sorted(Comparator.comparing(Session::getStartDate)).toList();
		
		for (int i = 0; i < sortedSessions.size(); i++) {
			Session currentSession = sortedSessions.get(i);
			
			if (currentSession.contains(date) && i - 1 >= 0) {
				return Optional.ofNullable(sortedSessions.get(i-1));
			}
		}
		
		return Optional.empty();
	}
	
    @Override
    public Optional<Session> getSession(LocalDate date) {
        Set<Session> sessions =
                readSessions().stream().map(sessionMapper::toDomain).collect(Collectors.toSet());
        return sessions.stream().filter(session -> session.contains(date)).findFirst();
    }
	
	private Set<SessionRecord> readSessions() {
        try (InputStream input = Files.newInputStream(this.resourcePath)) {
            Set<SessionRecord> sessionRecords =
                    this.jsonMapper.readValue(input, new TypeReference<>() {});

            return adjustSessionsEndDate(sessionRecords);
        } catch (IOException e) {
            throw new SessionException("Failed to fetch sessions");
        }
    }

    private Set<SessionRecord> adjustSessionsEndDate(Set<SessionRecord> sessionRecords) {
        List<SessionRecord> sortedSessions = sessionRecords.stream()
                .sorted(Comparator.comparing(SessionRecord::startDate)).toList();

        Set<SessionRecord> adjustedSessions = new LinkedHashSet<>();

        for (int i = 0; i < sortedSessions.size(); i++) {
            SessionRecord current = sortedSessions.get(i);
            LocalDate start = current.startDate();
            LocalDate end;

            if (i + 1 < sortedSessions.size()) {
                SessionRecord next = sortedSessions.get(i + 1);
                end = next.startDate().minusDays(1);
            } else {
                end = current.endDate();
            }

            adjustedSessions.add(new SessionRecord(current.semesterCode(), start, end));
        }

        return adjustedSessions;
    }
}
