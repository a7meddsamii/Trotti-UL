package ca.ulaval.glo4003.trotti.domain.commons;

import ca.ulaval.glo4003.trotti.domain.order.Session;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class SessionRegistry {
	List<Session> sessions;
	
	public SessionRegistry(List<Session> sessions) {
		this.sessions = sessions;
	}
	
	public Optional<Session> getSession(LocalDate date) {
		return sessions.stream().filter(session -> session.contains(date)).findFirst();
	}	
}
