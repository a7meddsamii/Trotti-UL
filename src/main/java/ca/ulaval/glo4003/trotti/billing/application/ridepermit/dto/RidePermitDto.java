package ca.ulaval.glo4003.trotti.billing.application.ridepermit.dto;

import ca.ulaval.glo4003.trotti.billing.domain.order.values.Session;
import ca.ulaval.glo4003.trotti.billing.domain.payment.values.money.Money;
import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.values.RidePermitId;
import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.values.RidePermitState;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;

import java.time.Duration;

public record RidePermitDto(
		RidePermitId ridePermitId,
		Idul riderId,
		Session session,
		Duration maximumTravelingTimePerDay,
		RidePermitState permitState,
		Money balance
) {
}
