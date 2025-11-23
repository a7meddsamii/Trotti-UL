package ca.ulaval.glo4003.trotti.billing.application.order.dto;

import ca.ulaval.glo4003.trotti.billing.domain.order.values.BillingFrequency;
import ca.ulaval.glo4003.trotti.billing.domain.order.values.MaximumDailyTravelTime;
import ca.ulaval.glo4003.trotti.billing.domain.order.values.Session;

public record AddItemDto(
		MaximumDailyTravelTime maximumDailyTravelTime,
		Session session,
		BillingFrequency billingFrequency) {}
