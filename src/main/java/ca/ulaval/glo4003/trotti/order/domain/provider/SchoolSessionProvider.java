package ca.ulaval.glo4003.trotti.order.domain.provider;

import ca.ulaval.glo4003.trotti.order.domain.values.Session;
import java.time.LocalDate;
import java.util.Optional;

public interface SchoolSessionProvider {
    Optional<Session> getSession(LocalDate date);
}
