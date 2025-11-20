package ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.records;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.order.domain.values.Session;
import ca.ulaval.glo4003.trotti.trip.domain.values.RidePermitId;

public record RidePermitRecord(RidePermitId permitId, Idul idul, Session session) {
}
