package ca.ulaval.glo4003.trotti.infrastructure.trip.records;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.order.Session;

public record RidePermitRecord(Id permitId, Idul idul, Session session) {
}
