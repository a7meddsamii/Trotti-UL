package ca.ulaval.glo4003.trotti.billing.domain.order.provider;

import ca.ulaval.glo4003.trotti.billing.domain.order.values.Session;

import java.time.LocalDate;
import java.util.Optional;

public interface SchoolSessionProvider {
    Optional<Session> getSession(LocalDate date);
}
