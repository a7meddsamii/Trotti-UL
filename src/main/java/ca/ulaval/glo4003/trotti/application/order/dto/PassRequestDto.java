package ca.ulaval.glo4003.trotti.application.order.dto;

import ca.ulaval.glo4003.trotti.domain.order.Session;
import ca.ulaval.glo4003.trotti.domain.order.values.BillingFrequency;
import ca.ulaval.glo4003.trotti.domain.order.values.MaximumDailyTravelTime;

public record PassRequestDto(
    MaximumDailyTravelTime maximumDailyTravelTime,
    Session session,
    BillingFrequency billingFrequency
) {
}
