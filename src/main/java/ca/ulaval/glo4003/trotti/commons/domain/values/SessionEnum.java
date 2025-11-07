package ca.ulaval.glo4003.trotti.commons.domain.values;

import ca.ulaval.glo4003.trotti.order.domain.values.Session;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class SessionEnum {
    List<Session> sessions;

    public SessionEnum(List<Session> sessions) {
        this.sessions = sessions;
    }

    public Optional<Session> getSession(LocalDate date) {
        return sessions.stream().filter(session -> session.contains(date)).findFirst();
    }
}
