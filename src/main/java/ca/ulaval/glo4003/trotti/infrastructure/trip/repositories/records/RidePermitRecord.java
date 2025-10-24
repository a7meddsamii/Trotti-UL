package ca.ulaval.glo4003.trotti.infrastructure.trip.repositories.records;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.order.values.Session;
import ca.ulaval.glo4003.trotti.domain.trip.values.RidePermitId;

public record RidePermitRecord(RidePermitId permitId, Idul idul, Session session) {
}
