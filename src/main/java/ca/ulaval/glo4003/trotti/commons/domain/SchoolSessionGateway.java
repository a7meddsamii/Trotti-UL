package ca.ulaval.glo4003.trotti.commons.domain;

import ca.ulaval.glo4003.trotti.order.domain.values.Session;

import java.time.LocalDate;
import java.util.Optional;

public interface SchoolSessionGateway {
	Optional<Session> getSession(LocalDate date);
}
