package ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.records;

import ca.ulaval.glo4003.trotti.commons.domain.values.ids.Idul;
import ca.ulaval.glo4003.trotti.commons.domain.values.session.Session;
import ca.ulaval.glo4003.trotti.trip.domain.values.RidePermitId;

public record RidePermitRecord(RidePermitId permitId, Idul idul, Session session) {
}
