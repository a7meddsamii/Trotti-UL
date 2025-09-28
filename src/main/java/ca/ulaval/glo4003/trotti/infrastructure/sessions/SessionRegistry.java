package ca.ulaval.glo4003.trotti.infrastructure.sessions;

import ca.ulaval.glo4003.trotti.domain.order.Session;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class SessionRegistry {
    private final Map<String, Session> sessions;

    public SessionRegistry() {
        var sessionList = SessionLoader.loadSessions();
        this.sessions = Collections.unmodifiableMap(
                sessionList.stream()
                        .collect(Collectors.toMap(Session::getSemesterCode, Function.identity()))
        );
        sessionList.forEach(session -> System.out.println(session.getSemesterCode()));
    }

    public Session getSession(String code) {
        return sessions.get(code);
    }

    public boolean isValid(String code) {
        return sessions.containsKey(code);
    }

    public Map<String, Session> getAllSessions() {
        return sessions;
    }
}
