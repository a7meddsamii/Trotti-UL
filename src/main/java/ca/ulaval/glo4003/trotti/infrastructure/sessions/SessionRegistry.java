package ca.ulaval.glo4003.trotti.infrastructure.sessions;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class SessionRegistry {
    private final Map<String, SessionDTO> sessions;

    public SessionRegistry() {
        var sessionList = SessionLoader.loadSessions();
        this.sessions = Collections.unmodifiableMap(
                sessionList.stream()
                        .collect(Collectors.toMap(SessionDTO::semesterCode, Function.identity()))
        );
    }

    public SessionDTO getSession(String code) {
        return sessions.get(code);
    }

    public boolean isValid(String code) {
        return sessions.containsKey(code);
    }
}
