package ca.ulaval.glo4003.trotti.billing.application.order.dto;

import ca.ulaval.glo4003.trotti.billing.domain.order.values.BillingFrequency;
import ca.ulaval.glo4003.trotti.billing.domain.order.values.ItemId;
import ca.ulaval.glo4003.trotti.billing.domain.order.values.MaximumDailyTravelTime;
import ca.ulaval.glo4003.trotti.billing.domain.order.values.Session;
import ca.ulaval.glo4003.trotti.billing.domain.payment.values.money.Money;

public record RidePermitItemDto(
		ItemId itemId,
		MaximumDailyTravelTime maximumDailyTravelTime,
		Session session,
		BillingFrequency billingFrequency,
		Money cost) {
	
}
